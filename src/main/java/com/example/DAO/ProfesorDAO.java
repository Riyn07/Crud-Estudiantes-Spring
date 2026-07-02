package com.example.DAO;

import com.example.entities.Profesores;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProfesorDAO extends JpaRepository<Profesores, Integer> {

}
