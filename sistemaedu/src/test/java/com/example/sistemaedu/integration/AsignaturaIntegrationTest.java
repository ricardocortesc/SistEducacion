package com.example.sistemaedu.integration;

import com.example.sistemaedu.controller.DTO.AsignaturaDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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
public class AsignaturaIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void When_guardarAsignatura_Then_returnAsignaturaGuardada() {
        AsignaturaDTO nuevaAsignatura = new AsignaturaDTO(1L, "Matemáticas", 4);
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/asignatura", nuevaAsignatura, String.class);
        Assertions.assertEquals("Asignatura guardada", respuestaInsercion.getBody());
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/asignaturas-bd", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertTrue(resultado.getStatusCode().is2xxSuccessful());
    }

    @Test
    void When_obtenerAsignaturaPorId_Then_returnAsignatura() {
        AsignaturaDTO asignaturaBuscar = new AsignaturaDTO(1L, "Física", 3);
        testRestTemplate.postForEntity("/asignatura", asignaturaBuscar, String.class);
        ResponseEntity<AsignaturaDTO> respuestaObtenerAsignatura = testRestTemplate.getForEntity("/asignaturas/1", AsignaturaDTO.class);

        Assertions.assertTrue(respuestaObtenerAsignatura.getStatusCode().is2xxSuccessful());
        AsignaturaDTO asignaturaObtenida = respuestaObtenerAsignatura.getBody();
        Assertions.assertNotNull(asignaturaObtenida);
        Assertions.assertEquals(asignaturaBuscar.nombre(), asignaturaObtenida.nombre());
        Assertions.assertEquals(asignaturaBuscar.creditos(), asignaturaObtenida.creditos());
    }

    @Test
    void When_actualizarAsignatura_Then_returnAsignaturaActualizada() {
        AsignaturaDTO asignaturaInicial = new AsignaturaDTO(2L, "Química", 4);
        testRestTemplate.postForEntity("/asignatura", asignaturaInicial, String.class);
        AsignaturaDTO asignaturaActualizada = new AsignaturaDTO(2L, "Química Avanzada", 5);
        ResponseEntity<String> respuestaActualizacion = testRestTemplate.exchange("/asignatura/2", HttpMethod.PUT, new HttpEntity<>(asignaturaActualizada), String.class);
        Assertions.assertEquals("Asignatura actualizada correctamente", respuestaActualizacion.getBody());

        ResponseEntity<AsignaturaDTO> resultadoConsulta = testRestTemplate.getForEntity("/asignaturas/2", AsignaturaDTO.class);
        AsignaturaDTO asignaturaConsultada = resultadoConsulta.getBody();
        Assertions.assertNotNull(asignaturaConsultada);
        Assertions.assertEquals("Química Avanzada", asignaturaConsultada.nombre());
        Assertions.assertEquals(5, asignaturaConsultada.creditos());
    }

    @Test
    void When_eliminarAsignatura_Then_returnAsignaturaEliminada(){
        AsignaturaDTO asignaturaParaEliminar = new AsignaturaDTO(2L, "Química", 4);
        testRestTemplate.postForEntity("/asignatura", asignaturaParaEliminar, String.class);

        testRestTemplate.delete("/asignaturaEliminada/2");

        AsignaturaDTO asignaturaEliminada = testRestTemplate.getForObject("/asignaturas/3", AsignaturaDTO.class);
        Assertions.assertNull(asignaturaEliminada);

    }
}
