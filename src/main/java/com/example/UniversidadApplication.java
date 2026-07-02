package com.example;


import com.example.entities.Facultad;
import com.example.entities.Profesores;
import com.example.model.Genero;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@SpringBootApplication
public class UniversidadApplication implements CommandLineRunner {

	private final FacultadService facultadService;
	private final ProfesorService profesorService;

	public static void main(String[] args) {
		SpringApplication.run(UniversidadApplication.class, args);
	}

	@Override
	public void run(String @NonNull ... args) throws Exception {

		Facultad facultad1 = Facultad.builder()
				.nombre("Facultad de Ciencias")
				.build();
		Facultad facultad2 = Facultad.builder()
				.nombre("Facultad de Matemáticas")
				.build();

		facultadService.saveFacultad(facultad1);
		facultadService.saveFacultad(facultad2);

	   Profesores profesores = Profesores.builder()
				.nombre("Juan")
				.primerApellido("Perez")
				.segundoApellido("Garcia")
			    .genero(Genero.MASCULINO)
			    .facultad(facultad1)
			    .fechaAlta(LocalDate.now())
			    .salario(BigDecimal.valueOf(2500.0))
				.build();
	   Profesores profesores2 = Profesores.builder()
				.nombre("Maria")
				.primerApellido("Lopez")
				.segundoApellido("Martinez")
			    .genero(Genero.FEMENINO)
			    .facultad(facultad2)
			    .fechaAlta(LocalDate.now())
			    .salario(BigDecimal.valueOf(3000.0))
				.build();
	   Profesores profesores3 = Profesores.builder()
				.nombre("Carlos")
				.primerApellido("Gomez")
				.segundoApellido("Rodriguez")
			    .genero(Genero.MASCULINO)
			    .facultad(facultad1)
			    .fechaAlta(LocalDate.now())
			    .salario(BigDecimal.valueOf(2800.0))
				.build();
	   Profesores profesores4 = Profesores.builder()
				.nombre("Ana")
				.primerApellido("Sanchez")
				.segundoApellido("Diaz")
			    .genero(Genero.FEMENINO)
			    .facultad(facultad2)
			    .fechaAlta(LocalDate.now())
			    .salario(BigDecimal.valueOf(3200.0))
				.build();
		profesorService.saveProfesor(profesores);
		profesorService.saveProfesor(profesores2);
		profesorService.saveProfesor(profesores3);
		profesorService.saveProfesor(profesores4);
	}
}
