package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.controller.DTO.ProfesorDTO;
import com.example.sistemaedu.logica.ProfesorService;
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

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Transactional
public class ProfesorIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorJPA profesorJPA;

    @BeforeEach
    void limpiarBaseDeDatos() {
        profesorJPA.deleteAll();
    }

    @Test
    void When_guardarProfesor_Then_returnProfesorGuardado() {
        ProfesorDTO nuevoProfesor = new ProfesorDTO(1L, "Maria Lopez", "Femenino", 30, "Matemáticas", "Profesor", "maria.lopez@example.com");
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/profesor", nuevoProfesor, String.class);
        Assertions.assertEquals("Profesor guardado", respuestaInsercion.getBody());
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/profesores-bd", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertEquals(1, resultado.getBody().size()); // Asegurarse que solo hay un profesor
    }

    @Test
    void When_obtenerProfesorPorId_Then_returnProfesor() {
        ProfesorDTO profesorBuscar = new ProfesorDTO(1L, "Maria Lopez", "Femenino", 30, "Matemáticas", "Profesor", "maria.lopez@example.com");
        testRestTemplate.postForEntity("/profesor", profesorBuscar, String.class);
        ResponseEntity<ProfesorDTO> respuestaObtenerProfesor = testRestTemplate.getForEntity("/profesores/1", ProfesorDTO.class);

        Assertions.assertTrue(respuestaObtenerProfesor.getStatusCode().is2xxSuccessful());
        ProfesorDTO profesorObtenido = respuestaObtenerProfesor.getBody();
        Assertions.assertNotNull(profesorObtenido);
        Assertions.assertEquals(profesorBuscar.codigo(), profesorObtenido.codigo());
        Assertions.assertEquals(profesorBuscar.nombre(), profesorObtenido.nombre());
        Assertions.assertEquals(profesorBuscar.genero(), profesorObtenido.genero());
        Assertions.assertEquals(profesorBuscar.edad(), profesorObtenido.edad());
        Assertions.assertEquals(profesorBuscar.departamento(), profesorObtenido.departamento());
        Assertions.assertEquals(profesorBuscar.cargo(), profesorObtenido.cargo());
        Assertions.assertEquals(profesorBuscar.email(), profesorObtenido.email());
    }

    @Test
    void When_actualizarProfesor_Then_returnProfesorActualizado() {
        ProfesorDTO profesorInicial = new ProfesorDTO(1L, "Maria Lopez", "Femenino", 30, "Matemáticas", "Profesor", "maria.graciela@example.com");
        testRestTemplate.postForEntity("/profesor", profesorInicial, String.class);
        ProfesorDTO profesorActualizado = new ProfesorDTO(1L, "Maria Lopez", "Femenino", 31, "Matemáticas", "Profesor", "maria.nueva@example.com");

        ResponseEntity<String> respuestaActualizacion = testRestTemplate.exchange("/profesor/1", HttpMethod.PUT, new HttpEntity<>(profesorActualizado), String.class);
        Assertions.assertEquals("Profesor actualizado correctamente", respuestaActualizacion.getBody());

        ResponseEntity<ProfesorDTO> resultadoConsulta = testRestTemplate.getForEntity("/profesores/1", ProfesorDTO.class);
        ProfesorDTO profesorConsultado = resultadoConsulta.getBody();

        Assertions.assertNotNull(profesorConsultado);
        Assertions.assertEquals(31, profesorConsultado.edad());
        Assertions.assertEquals("maria.nueva@example.com", profesorConsultado.email());
    }


    @Test
    void When_actualizarProfesor_NonExistentId_Then_returnProfesorNoEncontrado() {
        ProfesorDTO profesorActualizado = new ProfesorDTO(99L, "No Existe", "Desconocido", 40, "Carrera Inexistente", "noexiste@example.com", "Cargo Inexistente");
        ResponseEntity<String> respuestaActualizacion = testRestTemplate.exchange("/profesor/99", HttpMethod.PUT, new HttpEntity<>(profesorActualizado), String.class);
        Assertions.assertEquals("Profesor no encontrado", respuestaActualizacion.getBody());
    }

    @Test
    void When_eliminarProfesor_Then_profesorEliminado() {
        ProfesorDTO profesorParaEliminar = new ProfesorDTO(3L, "Pedro Ruiz", "Masculino", 35, "Ciencias", "Profesor", "pedro.ruiz@example.com");
        testRestTemplate.postForEntity("/profesor", profesorParaEliminar, String.class);
        testRestTemplate.delete("/profesorEliminado/3");
        ProfesorDTO profesorEliminado = testRestTemplate.getForObject("/profesores/3", ProfesorDTO.class);
        Assertions.assertNull(profesorEliminado);
    }
}
