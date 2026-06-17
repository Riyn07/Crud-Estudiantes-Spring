package com.example.services;

import com.example.DAO.FacultadDAO;
import com.example.entities.Facultad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
