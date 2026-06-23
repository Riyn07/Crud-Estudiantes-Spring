package com.example.controller;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.logging.Logger;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger logger = Logger.getLogger(EstudianteController.class.getName());
    private final FacultadService facultadService;
    private final EstudianteService estudianteService;

    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "ListadoEstudiantes";
    }

    @GetMapping("/Alta")
    public String mostrarFormulario(Model model) {
        Estudiante estudiante = new Estudiante();
        estudiante.setTelefonos(new ArrayList<>());
        estudiante.setEmails(new ArrayList<>());
        estudiante.addTelefono(new Telefono());
        estudiante.addCorreo(new Correo());

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("facultades", facultadService.getAllFacultades());
        return "AltaEstudiante";
    }

    @PostMapping("/Save")
    public String procesarFormularioAltaModificacion(
            @Valid @ModelAttribute Estudiante estudiante,
            BindingResult result,
            Model model,
            @RequestParam (name= "file", required = false) MultipartFile file) throws IOException {

        for (int i = 0; i < estudiante.getEmails().size(); i++) {
            String direccion = estudiante.getEmails().get(i).getDireccion();
            if (direccion == null || direccion.isBlank()) {
                result.rejectValue("emails[" + i + "].direccion", "error.correo", "El correo no puede estar vacío");
            } else if (!direccion.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
                result.rejectValue("emails[" + i + "].direccion", "error.correo", "El formato del correo no es válido");
            }
        }


        for (int i = 0; i < estudiante.getTelefonos().size(); i++) {
            String numero = estudiante.getTelefonos().get(i).getNumero();
            if (numero == null || numero.isBlank()) {
                result.rejectValue("telefonos[" + i + "].numero", "error.telefono", "El teléfono no puede estar vacío");
            } else if (!numero.matches("^[0-9+\\s\\-]{7,15}$")) {
                result.rejectValue("telefonos[" + i + "].numero", "error.telefono", "El teléfono solo puede contener números y tener entre 7 y 15 dígitos");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("facultades", facultadService.getAllFacultades());
            return "AltaEstudiante";
        }

        if (file != null && file.isEmpty()); {

            Path rutaRelativa = Paths.get("src/main/resources/static/images"");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            Path rutaCompleta = Path.of(RutaAbsoluta + "/" + file.getOriginalFilename());

            Try {
                byte[] bytes = file.getBytes();
                Files.write(rutaCompleta, bytes);
                estudiante.setFoto(file.getOriginalFilename());
            } catch (IOException e) {
                logger.severe("Error al guardar la foto: " + e.getMessage());
                result.rejectValue("foto", "error.foto", "Error al guardar la foto");
                model.addAttribute("facultades", facultadService.getAllFacultades());
                return "AltaEstudiante";
            }
        }

        if (estudiante.getFacultad() != null && estudiante.getFacultad().getId() != 0) {
            facultadService.getFacultadById(estudiante.getFacultad().getId())
                    .ifPresent(estudiante::setFacultad);
        } else {
            estudiante.setFacultad(null);
        }

        estudiante.getTelefonos().forEach(t -> t.setEstudiante(estudiante));
        estudiante.getEmails().forEach(c -> c.setEstudiante(estudiante));

        estudianteService.saveEstudiante(estudiante);

        return "redirect:/estudiantes/listar";


    }
}