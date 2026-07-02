package com.example.controller;

import com.example.entities.Profesores;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;
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
import java.util.logging.Logger;

@Controller
@RequestMapping("/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private static final Logger logger =
            Logger.getLogger(ProfesorController.class.getName());

    private final FacultadService facultadService;
    private final ProfesorService profesorService;


    @GetMapping("/listar")
    public String listarProfesores(Model model) {

        model.addAttribute(
                "profesores",
                profesorService.getAllProfesores()
        );

        return "ListadoProfesor";
    }

    @GetMapping("/alta")
    public String mostrarFormulario(Model model) {

        Profesores profesor = new Profesores();

        model.addAttribute("profesor", profesor);
        model.addAttribute(
                "facultades",
                facultadService.getAllFacultades()
        );

        return "AltaProfesor";
    }


    @PostMapping("/save")
    public String guardarProfesor(
            @Valid @ModelAttribute Profesores profesor,
            BindingResult result,
            Model model,
            @RequestParam(name = "file", required = false)
            MultipartFile file
    ) throws IOException {

        if (file != null && !file.isEmpty()) {

            Path ruta = Paths.get("src/main/resources/static/images");
            Path archivo = ruta.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(ruta);
                Files.write(archivo, file.getBytes());

                profesor.setFoto(file.getOriginalFilename());

            } catch (IOException e) {

                logger.severe("Error foto: " + e.getMessage());

                result.rejectValue("foto", "error.foto", "Error al guardar foto");

                model.addAttribute("facultades", facultadService.getAllFacultades());

                return "AltaProfesor";
            }
        }


        // FACULTAD
        if (profesor.getFacultad() != null &&
                profesor.getFacultad().getId() != 0) {

            facultadService.getFacultadById(
                    profesor.getFacultad().getId()
            ).ifPresent(profesor::setFacultad);

        } else {
            profesor.setFacultad(null);
        }

        profesorService.saveProfesor(profesor);

        return "redirect:/profesores/listar";
    }


    @GetMapping("/details/{id}")
    public String detalleProfesor(@PathVariable int id, Model model) {

        Profesores profesor =
                profesorService.getProfesorById(id);

        if (profesor == null) {
            return "redirect:/profesores/listar";
        }

        model.addAttribute("profesor", profesor);

        return "DetalleProfesor";
    }


    @GetMapping("/update/{id}")
    public String updateProfesor(@PathVariable int id, Model model) {

        Profesores profesor =
                profesorService.getProfesorById(id);

        model.addAttribute("profesor", profesor);
        model.addAttribute(
                "facultades",
                facultadService.getAllFacultades()
        );

        return "AltaProfesor";
    }

    @GetMapping("/delete/{idProfesor}")
    public String deleteProfesor(@PathVariable int idProfesor, Model model) {

        Profesores profesorEliminar = profesorService.getProfesorById(idProfesor);

        if (profesorEliminar.getFoto() != null) {

            Path rutaRelativa = Paths.get("src/main/resources/static/images" + profesorEliminar.getFoto());
            try {
                Files.deleteIfExists(rutaRelativa);
            } catch (IOException e) {
                logger.severe("Error al eliminar la foto: " + e.getMessage());

            }
        }

        profesorService.deleteProfesor(profesorEliminar);

        return "redirect:/profesores/listar";
    }
}