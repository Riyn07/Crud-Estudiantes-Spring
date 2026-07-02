package com.example.services;

import com.example.entities.Profesores;

import java.util.List;

public interface ProfesorService {
    List<Profesores> getAllProfesores();

    Profesores getProfesorById(int id);

    Profesores saveProfesor(Profesores profesor);

    void deleteProfesor(int id);

    void deleteProfesor(Profesores profesor);

    Profesores updateProfesor(Profesores profesor);

}
