package com.example.services;

import com.example.DAO.CorreoDAO;
import com.example.entities.Correo;
import com.example.entities.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class CorreoServiceIMPL implements CorreoService {

    private final CorreoDAO correoDAO;

    @Override
    public Correo saveCorreo(Correo correo) {
        return correoDAO.save(correo);
    }
    @Override
    public List<Correo> getAllCorreos() {
        return correoDAO.findAll();
    }
    @Override
    public boolean existByEstudiante(Estudiante estudiante) {
        return correoDAO.existsByEstudiante(estudiante);
    }
    @Override
    public List<Estudiante> deleteByEstudiante(Estudiante estudiante) {
        correoDAO.deleteByEstudiante(estudiante);
        return null;
    }
    @Override
    public List<Estudiante>  findByEstudiante(Estudiante estudiante) {
        return correoDAO.findByEstudiante(estudiante);
    }

}
