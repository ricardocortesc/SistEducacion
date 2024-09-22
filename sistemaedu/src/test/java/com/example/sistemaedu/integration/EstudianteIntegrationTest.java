package com.example.sistemaedu.integration;

import com.example.sistemaedu.controller.DTO.EstudianteDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class EstudianteIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void cuandoGuardarEstudiante_deberiaRetornarEstudianteGuardado() {
        EstudianteDTO nuevoEstudiante = new EstudianteDTO(18L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "juaness@gmail.com", 5, 4.5f);

        // Realizar la inserción
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/estudiante", nuevoEstudiante, String.class);
        System.out.println(respuestaInsercion);

        // Verificar que la respuesta sea correcta
        Assertions.assertEquals("Estudiante guardado", respuestaInsercion.getBody());

        // Obtener la lista de estudiantes y verificar que el nuevo estudiante está presente

        // También podrías verificar que el estudiante específico fue añadido
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/estudiantes/18", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertTrue(resultado.getStatusCode().is2xxSuccessful());
    }

}
