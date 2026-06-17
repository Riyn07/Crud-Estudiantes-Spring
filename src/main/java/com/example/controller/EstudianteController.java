package com.example.controller;


import com.example.entities.Estudiante;
import com.example.model.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final FacultadService facultadService;
    private final EstudianteService estudianteService;

    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {

        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "ListadoEstudiantes";
    }

    @GetMapping("/Alta")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultadService.getAllFacultades());

        return "AltaEstudiante";
    }

    }

