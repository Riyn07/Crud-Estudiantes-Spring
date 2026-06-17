package com.example.services;

import com.example.entities.Estudiante;

import java.util.List;

public interface EstudianteService {
    List<Estudiante> getAllEstudiantes();

    Estudiante getEstudianteById(int id);

    Estudiante saveEstudiante(Estudiante estudiante);

    void deleteEstudiante(int id);

    void deleteEstudiante(Estudiante estudiante);

    Estudiante updateEstudiante(Estudiante estudiante);




}
