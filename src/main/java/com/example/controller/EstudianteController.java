package com.example.controller;


import com.example.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {

        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "ListadoEstudiantes";
    }
}

