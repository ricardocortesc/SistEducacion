package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.controller.DTO.AsignaturaDTO;
import com.example.sistemaedu.controller.DTO.EstudianteDTO;
import com.example.sistemaedu.controller.DTO.ProfesorDTO;
import com.example.sistemaedu.logica.AsignaturaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Transactional
public class AsignaturaIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private AsignaturaJPA asignaturaJPA;

    @BeforeEach
    void limpiarBaseDeDatos() {
        asignaturaJPA.deleteAll();
    }

    @Test
    void When_guardarAsignatura_Then_returnAsignaturaGuardada() {
        // Paso 1: Crear un profesor
        ProfesorDTO profesorDTO = new ProfesorDTO(1L, "Carlos López", "Masculino", 23, "Ingeniería", "Planta", "ejemp@gmail.com");
        ResponseEntity<String> respuestaProfesor = testRestTemplate.postForEntity("/profesor", profesorDTO, String.class);
        Assertions.assertEquals("Profesor guardado", respuestaProfesor.getBody());

        // Paso 2: Crear estudiantes con correos únicos
        EstudianteDTO estudianteDTO = new EstudianteDTO(1L, "Juan Pérez", "Masculino", 20, "Ingeniería de Sistemas", "juanpereza@example.com", 3, 4.5f);
        ResponseEntity<String> respuestaEstudiante = testRestTemplate.postForEntity("/estudiante", estudianteDTO, String.class);
        Assertions.assertEquals("Estudiante guardado", respuestaEstudiante.getBody());

        EstudianteDTO estudianteDTO2 = new EstudianteDTO(2L, "Juan Ares", "Masculino", 20, "Ingeniería de Sistemas", "juan.ares@example.com", 3, 4.5f);
        ResponseEntity<String> respuestaEstudiante2 = testRestTemplate.postForEntity("/estudiante", estudianteDTO2, String.class);
        Assertions.assertEquals("Estudiante guardado", respuestaEstudiante2.getBody());

        // Paso 3: Crear una asignatura vinculada al profesor y a los estudiantes
        AsignaturaDTO nuevaAsignatura = new AsignaturaDTO(1L, "Matemáticas", "Asignatura de álgebra", 4, 1L, List.of(1L, 2L));
        ResponseEntity<String> respuestaAsignatura = testRestTemplate.postForEntity("/asignatura", nuevaAsignatura, String.class);
        Assertions.assertEquals("Asignatura guardada con estudiantes", respuestaAsignatura.getBody());

        // Paso 4: Verificar que la asignatura fue creada correctamente en la base de datos
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/asignaturas", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertEquals(1, resultado.getBody().size()); // Asegúrate de que solo hay una asignatura
    }




    @Test
    void When_obtenerAsignaturaPorId_Then_returnAsignatura() {
        // Paso 1: Crear un profesor
        ProfesorDTO profesorDTO = new ProfesorDTO(1L, "Carlos López", "Masculino", 23, "Ingeniería", "Planta", "yanose@gmail.com");
        ResponseEntity<String> respuestaProfesor = testRestTemplate.postForEntity("/profesor", profesorDTO, String.class);
        Assertions.assertEquals("Profesor guardado", respuestaProfesor.getBody());

        // Paso 2: Crear un estudiante con un correo único
        EstudianteDTO estudianteDTO = new EstudianteDTO(1L, "Juan Pérez", "Masculino", 20, "Ingeniería de Sistemas", "otro@example.com", 3, 4.5f);
        ResponseEntity<String> respuestaEstudiante = testRestTemplate.postForEntity("/estudiante", estudianteDTO, String.class);
        Assertions.assertEquals("Estudiante guardado", respuestaEstudiante.getBody());

        // Paso 3: Crear una asignatura vinculada al profesor y al estudiante
        AsignaturaDTO asignaturaBuscar = new AsignaturaDTO(1L, "Física", "Asignatura de mecánica", 4, 1L, List.of(1L));
        ResponseEntity<String> respuestaAsignatura = testRestTemplate.postForEntity("/asignatura", asignaturaBuscar, String.class);
        Assertions.assertEquals("Asignatura guardada con estudiantes", respuestaAsignatura.getBody());

        // Paso 4: Obtener la asignatura por su ID
        ResponseEntity<AsignaturaDTO> respuestaObtenerAsignatura = testRestTemplate.getForEntity("/asignatura/1", AsignaturaDTO.class);
        Assertions.assertTrue(respuestaObtenerAsignatura.getStatusCode().is2xxSuccessful());
        AsignaturaDTO asignaturaObtenida = respuestaObtenerAsignatura.getBody();

        // Paso 5: Verificar que la asignatura obtenida sea correcta
        Assertions.assertNotNull(asignaturaObtenida);
        Assertions.assertEquals(asignaturaBuscar.id(), asignaturaObtenida.id());
        Assertions.assertEquals(asignaturaBuscar.nombre(), asignaturaObtenida.nombre());
        Assertions.assertEquals(asignaturaBuscar.descripcion(), asignaturaObtenida.descripcion());
        Assertions.assertEquals(asignaturaBuscar.creditos(), asignaturaObtenida.creditos());
        Assertions.assertEquals(asignaturaBuscar.profesorId(), asignaturaObtenida.profesorId());
        Assertions.assertEquals(asignaturaBuscar.estudiantes(), asignaturaObtenida.estudiantes());
    }




    @Test
    void When_eliminarAsignatura_Then_asignaturaEliminada() {

        // Paso 4: Eliminar la asignatura
        testRestTemplate.delete("/asignaturaEliminada/1");

        AsignaturaDTO asignaturaEliminada = testRestTemplate.getForObject("/asignatura/1", AsignaturaDTO.class);
        // Verificar que la asignatura no existe
        Assertions.assertNull("asignaturaEliminada");
    }


}
