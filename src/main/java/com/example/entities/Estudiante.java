package com.example.entities;

import com.example.model.Genero;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "estudiantes")
@ToString(exclude = {"emails", "facultad"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private List<Telefono> telefonos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private List<Correo> emails = new ArrayList<>();

    public void addTelefono(Telefono telefono) {
        telefonos.add(telefono);
        telefono.setEstudiante(this);
    }

    public void addCorreo(Correo correo) {
        emails.add(correo);
        correo.setEstudiante(this);
    }
}