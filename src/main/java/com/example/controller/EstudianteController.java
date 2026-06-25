package com.example.controller;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger logger =
            Logger.getLogger(EstudianteController.class.getName());

    private final FacultadService facultadService;
    private final EstudianteService estudianteService;
    private final CorreoService correoService;
    private final TelefonoService telefonoService;

    // =========================
    // LISTAR
    // =========================
    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {

        model.addAttribute(
                "estudiantes",
                estudianteService.getAllEstudiantes()
        );

        return "ListadoEstudiantes";
    }

    // =========================
    // ALTA
    // =========================
    @GetMapping("/alta")
    public String mostrarFormulario(Model model) {

        Estudiante estudiante = new Estudiante();

        estudiante.setTelefonos(new java.util.ArrayList<>());
        estudiante.setEmails(new java.util.ArrayList<>());

        estudiante.addTelefono(new Telefono());
        estudiante.addCorreo(new Correo());

        model.addAttribute("estudiante", estudiante);
        model.addAttribute(
                "facultades",
                facultadService.getAllFacultades()
        );

        return "AltaEstudiante";
    }

    // =========================
    // SAVE
    // =========================
    @PostMapping("/save")
    public String guardarEstudiante(
            @Valid @ModelAttribute Estudiante estudiante,
            @RequestParam String numerosTelefono,
            @RequestParam String direccionesCorreo,
            BindingResult result,
            Model model,
            @RequestParam(name = "file", required = false)
            MultipartFile file
    ) throws IOException {

        // VALIDACIÓN EMAIL
        for (int i = 0; i < estudiante.getEmails().size(); i++) {

            String direccion = estudiante.getEmails().get(i).getDireccion();

            if (direccion == null || direccion.isBlank()) {

                result.rejectValue(
                        "emails[" + i + "].direccion",
                        "error.correo",
                        "Correo vacío"
                );

            } else if (!direccion.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {

                result.rejectValue(
                        "emails[" + i + "].direccion",
                        "error.correo",
                        "Correo inválido"
                );
            }
        }

        // VALIDACIÓN TELÉFONO
        for (int i = 0; i < estudiante.getTelefonos().size(); i++) {

            String numero = estudiante.getTelefonos().get(i).getNumero();

            if (numero == null || numero.isBlank()) {

                result.rejectValue(
                        "telefonos[" + i + "].numero",
                        "error.telefono",
                        "Teléfono vacío"
                );

            } else if (!numero.matches("^[0-9+\\s\\-]{7,15}$")) {

                result.rejectValue(
                        "telefonos[" + i + "].numero",
                        "error.telefono",
                        "Teléfono inválido"
                );
            }
        }

        if (result.hasErrors()) {

            model.addAttribute(
                    "facultades",
                    facultadService.getAllFacultades()
            );

            return "AltaEstudiante";
        }

        // FOTO
        if (file != null && !file.isEmpty()) {

            Path ruta = Paths.get("src/main/resources/static/images");
            Path archivo = ruta.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(ruta);
                Files.write(archivo, file.getBytes());

                estudiante.setFoto(file.getOriginalFilename());

            } catch (IOException e) {

                logger.severe("Error foto: " + e.getMessage());

                result.rejectValue("foto", "error.foto", "Error al guardar foto");

                model.addAttribute("facultades", facultadService.getAllFacultades());

                return "AltaEstudiante";
            }
        }

        // TELÉFONOS
        if (numerosTelefono != null && !numerosTelefono.isBlank()) {

            Arrays.stream(numerosTelefono.split(";"))
                    .map(String::trim)
                    .filter(n -> !n.isEmpty())
                    .forEach(numero -> {

                        Telefono t = new Telefono();
                        t.setNumero(numero);
                        t.setEstudiante(estudiante);

                        estudiante.getTelefonos().add(t);
                    });
        }

        // CORREOS
        if (direccionesCorreo != null && !direccionesCorreo.isBlank()) {

            Arrays.stream(direccionesCorreo.split(";"))
                    .map(String::trim)
                    .filter(c -> !c.isEmpty())
                    .forEach(correoTexto -> {

                        Correo c = new Correo();
                        c.setDireccion(correoTexto);
                        c.setEstudiante(estudiante);

                        estudiante.getEmails().add(c);
                    });
        }

        // FACULTAD
        if (estudiante.getFacultad() != null &&
                estudiante.getFacultad().getId() != 0) {

            facultadService.getFacultadById(
                    estudiante.getFacultad().getId()
            ).ifPresent(estudiante::setFacultad);

        } else {
            estudiante.setFacultad(null);
        }

        estudianteService.saveEstudiante(estudiante);

        return "redirect:/estudiantes/listar";
    }

    // =========================
    // DETAILS
    // =========================
    @GetMapping("/details/{id}")
    public String detalleEstudiante(@PathVariable int id, Model model) {

        Estudiante estudiante =
                estudianteService.getEstudianteById(id);

        if (estudiante == null) {
            return "redirect:/estudiantes/listar";
        }

        model.addAttribute("estudiante", estudiante);

        return "DetalleEstudiante";
    }

    // =========================
    // UPDATE
    // =========================
    @GetMapping("/update/{id}")
    public String updateEstudiante(@PathVariable int id, Model model) {

        Estudiante estudiante =
                estudianteService.getEstudianteById(id);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute(
                "facultades",
                facultadService.getAllFacultades()
        );

        if (estudiante.getTelefonos() != null &&
                !estudiante.getTelefonos().isEmpty()) {

            String numeros = estudiante.getTelefonos().stream()
                    .map(Telefono::getNumero)
                    .collect(Collectors.joining(";"));

            model.addAttribute("numerosTelefono", numeros);
        }

        if (estudiante.getEmails() != null &&
                !estudiante.getEmails().isEmpty()) {

            String correos = estudiante.getEmails().stream()
                    .map(Correo::getDireccion)
                    .collect(Collectors.joining(";"));

            model.addAttribute("direccionesCorreos", correos);
        }

        return "AltaEstudiante";
    }

    @GetMapping("/delete/{idEstudiante}")
    public String deleteEstudiante(@PathVariable int idEstudiante, Model model) {

        Estudiante estudianteEliminar = estudianteService.getEstudianteById(idEstudiante);

        if (estudianteEliminar.getFoto() != null) {

            Path rutaRelativa = Paths.get("src/main/resources/static/images" + estudianteEliminar.getFoto());
            try {
                Files.deleteIfExists(rutaRelativa);
            } catch (IOException e) {
                logger.severe("Error al eliminar la foto: " + e.getMessage());

            }
        }

        estudianteService.deleteEstudiante(estudianteEliminar);

        return "redirect:/estudiantes/listar";
    }
}