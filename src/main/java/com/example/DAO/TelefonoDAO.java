package com.example.DAO;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelefonoDAO extends JpaRepository<Telefono, Integer> {

    boolean existsByEstudiante(Estudiante estudiante);

    void deleteByEstudiante(Estudiante estudiante);

    List<Telefono> findByEstudiante(Estudiante estudiante);
}