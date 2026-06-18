package com.example.controller;


import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.model.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
    public String procesarFormularioAltaModificacion(Estudiante estudiante, Model model) {

        estudiante.getTelefonos().forEach(t -> t.setEstudiante(estudiante));
        estudiante.getEmails().forEach(c -> c.setEstudiante(estudiante));

        logger.info("Procesando formulario de alta/modificación de estudiante: " + estudiante);

        estudianteService.saveEstudiante(estudiante);

        return "redirect:/estudiantes/listar";
    }



    }



