package com.example.services;

import com.example.DAO.ProfesorDAO;
import com.example.entities.Profesores;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class ProfesorServiceIMPL implements ProfesorService {

    private final ProfesorDAO profesorDAO;

    @Override
    public List<Profesores> getAllProfesores() {
        return profesorDAO.findAll();
    }

    @Override
    public Profesores getProfesorById(int id) {
        return profesorDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Profesor no encontrado con id: " + id));
    }
    @Override
    public Profesores saveProfesor(Profesores profesor) {
        return profesorDAO.save(profesor);
    }
    @Override
    public void deleteProfesor(int id) {
        profesorDAO.deleteById(id);
    }
    @Override
    public void deleteProfesor(Profesores profesor) {
        profesorDAO.delete(profesor);
    }
    @Override
    public Profesores updateProfesor(Profesores profesor) {
        return profesorDAO.save(profesor);
    }
}
