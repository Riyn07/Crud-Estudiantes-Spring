package com.example.entities;

import com.example.model.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Entity
@Table(name = "profesores")
@ToString(exclude = {"facultad"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profesores implements Serializable {

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

    @NotNull(message = "La fecha de alta es obligatoria")
    @PastOrPresent(message = "La fecha de alta no puede ser futura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAlta;

    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.01", message = "El salario debe ser un valor positivo")
    private BigDecimal salario;

    private String foto;

}