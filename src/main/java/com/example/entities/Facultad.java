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
    private Integer id;
    private String nombre;

    @OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profesores> profesores;

}
