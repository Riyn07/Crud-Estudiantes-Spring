package com.example;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.model.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@SpringBootApplication
public class UniversidadApplication implements CommandLineRunner {

	private final FacultadService facultadService;
	/*	private final TelefonoService telefonoService;*/
	private final EstudianteService estudianteService;
	/*private final CorreoService correoService;*/

	public static void main(String[] args) {
		SpringApplication.run(UniversidadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Facultad facultad1 = Facultad.builder()
				.nombre("Facultad de Ciencias")
				.build();
		Facultad facultad2 = Facultad.builder()
				.nombre("Facultad de Matematicas")
				.build();

		facultadService.saveFacultad(facultad1);
		facultadService.saveFacultad(facultad2);

		Estudiante estudiante1 = Estudiante.builder()
				.nombre("Pepe")
				.primerApellido("Lara")
				.segundoApellido("Gonzalez")
				.telefonos(List.of(
						Telefono.builder().numero("411412324").build(),
						Telefono.builder().numero("213454523").build()))
				.emails(List.of(Correo.builder().direccion("es1@gmail.com").build()))
				.fechaMatricula(LocalDate.now())
				.genero(Genero.MASCULINO)
				.facultad(facultad1)
				.build();

		Estudiante estudiante2 = Estudiante.builder()
				.nombre("Maria")
				.primerApellido("Gomez")
				.segundoApellido("Lopez")
				.telefonos(List.of(
						Telefono.builder().numero("987654321").build(),
						Telefono.builder().numero("456789123").build()))
				.emails(List.of(Correo.builder().direccion("es2@gmail.com").build()))
				.fechaMatricula(LocalDate.now())
				.genero(Genero.FEMENINO)
				.facultad(facultad2)
				.build();

		estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
		estudiante1.getEmails().forEach(correo -> correo.setEstudiante(estudiante1));
		estudiante2.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante2));
		estudiante2.getEmails().forEach(correo -> correo.setEstudiante(estudiante2));

		estudianteService.saveEstudiante(estudiante1);
		estudianteService.saveEstudiante(estudiante2);
	}
}
