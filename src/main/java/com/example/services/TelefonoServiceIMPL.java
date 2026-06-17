package com.example.services;

import com.example.DAO.TelefonoDAO;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class TelefonoServiceIMPL implements TelefonoService {

    private final TelefonoDAO telefonoDAO;

    @Override
    public Telefono saveTelefono(Telefono telefono) {
        return telefonoDAO.save(telefono);
    }

    @Override
    public List<Telefono> getAllTelefonos() {
        return telefonoDAO.findAll();
    }

    @Override
    public boolean existByEstudiante(Estudiante estudiante) {
        return telefonoDAO.existsByEstudiante(estudiante);
    }

    @Override
    public void deleteByEstudiante(Estudiante estudiante) {
        telefonoDAO.deleteByEstudiante(estudiante);
    }

    @Override
    public List<Telefono> findByEstudiante(Estudiante estudiante) {
        return telefonoDAO.findByEstudiante(estudiante);
    }
}
