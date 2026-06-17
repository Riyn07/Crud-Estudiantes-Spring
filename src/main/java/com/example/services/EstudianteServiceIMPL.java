package com.example.services;

import com.example.DAO.EstudianteDAO;
import com.example.entities.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class EstudianteServiceIMPL implements EstudianteService {

    private final EstudianteDAO estudianteDAO;

    @Override
    public List<Estudiante> getAllEstudiantes() {
        return estudianteDAO.findAll();
    }
    @Override
    public Estudiante getEstudianteById(int id) {
        return estudianteDAO.findById(id).orElseThrow(() ->
                new RuntimeException("Estudiante no encontrado con id: " + id));
    }
    @Override
    public Estudiante saveEstudiante(Estudiante estudiante) {
        return estudianteDAO.save(estudiante);
    }
    @Override
    public void deleteEstudiante(int id) {
        estudianteDAO.deleteById(id);
    }
    @Override
    public void deleteEstudiante(Estudiante estudiante) {
        estudianteDAO.delete(estudiante);
    }
    @Override
    public Estudiante updateEstudiante(Estudiante estudiante) {
        return estudianteDAO.save(estudiante);
    }
}
