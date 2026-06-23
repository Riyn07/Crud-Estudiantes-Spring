package com.example.services;

import com.example.entities.Facultad;

import java.util.List;
import java.util.Optional;

public interface FacultadService {
    Facultad saveFacultad(Facultad facultad);
    List<Facultad> getAllFacultades();
    Optional<Facultad> getFacultadById(int id);
}