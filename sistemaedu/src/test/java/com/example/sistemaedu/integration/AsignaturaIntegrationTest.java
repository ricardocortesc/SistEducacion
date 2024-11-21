package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import com.example.sistemaedu.controller.DTO.AsignaturaDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Transactional
public class AsignaturaIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AsignaturaJPA asignaturaJPA;

    @Autowired
    private EstudianteJPA estudianteJPA;

    @Autowired
    private ProfesorJPA profesorJPA;

    @BeforeEach
    void limpiarBaseDeDatos() {
        asignaturaJPA.deleteAll();
    }

    @Test
    void When_guardarAsignatura_Then_returnAsignaturaGuardada() {
        // Preparar datos de prueba para el Profesor con ORM
        ProfesorORM profesor = new ProfesorORM();
        profesor.setId(1L);                  // Asegúrate de que este ID será el mismo que usarás en el DTO
        profesor.setNombre("Profesor Ejemplo");
        profesor.setGenero("Masculino");
        profesor.setEdad(45);
        profesor.setDepartamento("Matemáticas");
        profesor.setCargo("Profesor Titular");
        profesor.setEmail("profesor@example.com");

        profesorJPA.save(profesor); // Guardar profesor en la base de datos

        // Preparar datos de prueba para el Estudiante
        List<EstudianteORM> estudiantes = new ArrayList<>();
        EstudianteORM estudiante = new EstudianteORM();
        estudiante.setId(3L);           // Asegúrate de que este ID también esté en la base de datos
        estudiantes.add(estudiante);
        estudianteJPA.save(estudiante); // Guardar estudiante en la base de datos

        // Crear y enviar la AsignaturaDTO
        AsignaturaDTO nuevaAsignatura = new AsignaturaDTO(1L, "Matemáticas Avanzadas", "Asignatura de matemáticas", 2, 1L, List.of(3L));

        // Realizar la petición de inserción de la asignatura
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/asignatura", nuevaAsignatura, String.class);
        Assertions.assertEquals("Asignatura guardada", respuestaInsercion.getBody());

        // Verificar que la asignatura se guardó correctamente
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/asignaturas", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertEquals(1, resultado.getBody().size()); // Verificar que solo hay una asignatura
    }


    @Test
    void When_obtenerAsignaturaPorId_Then_returnAsignatura() {
        // Preparar datos de prueba
        ProfesorORM profesor = new ProfesorORM();
        profesor.setId(2L);
        profesorJPA.save(profesor);

        List<EstudianteORM> estudiantes = new ArrayList<>();
        EstudianteORM estudiante = new EstudianteORM();
        estudiante.setId(2L);
        estudiantes.add(estudiante);
        estudianteJPA.save(estudiante);

        AsignaturaORM asignaturaGuardada = new AsignaturaORM("Matematicas", "Descripcion", 4, profesor, estudiantes);
        asignaturaJPA.save(asignaturaGuardada);

        // Hacer la solicitud de obtener la asignatura por ID
        ResponseEntity<AsignaturaDTO> respuestaObtenerAsignatura = testRestTemplate.getForEntity("/asignaturas/1", AsignaturaDTO.class);
        Assertions.assertTrue(respuestaObtenerAsignatura.getStatusCode().is2xxSuccessful());
        AsignaturaDTO asignaturaObtenida = respuestaObtenerAsignatura.getBody();

        // Verificar que los datos obtenidos sean los correctos
        Assertions.assertNotNull(asignaturaObtenida);
        Assertions.assertEquals("Matematicas", asignaturaObtenida.nombre());
        Assertions.assertEquals("Descripcion", asignaturaObtenida.descripcion());
        Assertions.assertEquals(4, asignaturaObtenida.creditos());
    }


    @Test
    void When_eliminarAsignatura_Then_asignaturaEliminada() {
        // Preparar datos de prueba
        ProfesorORM profesor = new ProfesorORM();
        profesor.setId(1L);
        profesorJPA.save(profesor);

        List<EstudianteORM> estudiantes = new ArrayList<>();
        EstudianteORM estudiante = new EstudianteORM();
        estudiante.setId(1L);
        estudiantes.add(estudiante);
        estudianteJPA.save(estudiante);

        AsignaturaORM asignaturaGuardada = new AsignaturaORM("Matematicas", "Descripcion", 4, profesor, estudiantes);
        asignaturaJPA.save(asignaturaGuardada);

        // Realizar la petición de eliminación
        testRestTemplate.delete("/asignatura/1");

        // Verificar que la asignatura fue eliminada
        ResponseEntity<AsignaturaDTO> resultadoConsulta = testRestTemplate.getForEntity("/asignaturas/1", AsignaturaDTO.class);

    }

}
