package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "correos")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Correo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estudiante estudiante;
}
