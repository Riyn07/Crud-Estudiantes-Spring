package com.example.services;

import com.example.DAO.FacultadDAO;
import com.example.entities.Facultad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FacultadServiceIMPL implements FacultadService {

    private final FacultadDAO facultadDAO;

    @Override
    public Facultad saveFacultad(Facultad facultad) {
        return facultadDAO.save(facultad);
    }

    @Override
    public List<Facultad> getAllFacultades() {
        return facultadDAO.findAll();
    }

    @Override
    public Optional<Facultad> getFacultadById(int id) {
        return facultadDAO.findById(id);
    }
}