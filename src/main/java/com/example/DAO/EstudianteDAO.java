package com.example.DAO;

import com.example.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EstudianteDAO extends JpaRepository<Estudiante, Integer> {

}
