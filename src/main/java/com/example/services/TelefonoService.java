package com.example.services;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;

import java.util.List;

public interface TelefonoService {
    Telefono saveTelefono(Telefono telefono);

    List<Telefono> getAllTelefonos();

    boolean existByEstudiante(Estudiante estudiante);

    void deleteByEstudiante(Estudiante estudiante);

    List<Telefono> findByEstudiante(Estudiante estudiante);
}
