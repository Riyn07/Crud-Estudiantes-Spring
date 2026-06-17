package com.example.DAO;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorreoDAO extends JpaRepository<Correo, Integer> {
    boolean existsByEstudiante(Estudiante estudiante);
    void deleteByEstudiante(Estudiante estudiante);
    List<Estudiante> findByEstudiante(Estudiante estudiante);
}
