package com.example.services;

import com.example.entities.Facultad;

import java.util.List;

public interface FacultadService {
    Facultad saveFacultad(Facultad facultad);

    List<Facultad> getAllFacultades();
}
