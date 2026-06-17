package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "facultades")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Facultad implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "facultad")
    private List<Estudiante> estudiantes;

}
