package com.example.services;

import com.example.entities.Correo;
import com.example.entities.Estudiante;

import java.util.List;

public interface CorreoService {

    Correo saveCorreo(Correo correo);

    List<Correo> getAllCorreos();

    boolean existByEstudiante(Estudiante estudiante);

    List<Estudiante> deleteByEstudiante(Estudiante estudiante);

    List<Estudiante>  findByEstudiante(Estudiante estudiante);
}
