package com.example.entities;

import com.example.model.Genero;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
@ToString(exclude = {"emails", "facultad", "telefonos"})
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

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$", message = "El nombre solo puede contener letras")
    private String nombre;

    @NotBlank(message = "El primer apellido no puede estar vacío")
    @Size(min = 2, max = 30, message = "El primer apellido debe tener entre 2 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$", message = "El primer apellido solo puede contener letras")
    private String primerApellido;

    @NotBlank(message = "El segundo apellido no puede estar vacío")
    @Size(min = 2, max = 30, message = "El segundo apellido debe tener entre 2 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$", message = "El segundo apellido solo puede contener letras")
    private String segundoApellido;

    @NotNull(message = "El género es obligatorio")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    @PastOrPresent(message = "La fecha de matrícula no puede ser futura")
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