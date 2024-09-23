package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.controller.DTO.EstudianteDTO;
import com.example.sistemaedu.logica.EstudianteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class EstudianteIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteJPA estudianteJPA;
    @Test
    void testGuardarEstudiante_Success() {
        boolean result = estudianteService.guardarEstudiante(1L, "Juan", "Masculino", 20, "Ingeniería", "juan@example.com", 4, 4.5f);
        assertTrue(result);

        EstudianteORM estudianteGuardado = estudianteJPA.findById(1L).orElse(null);
        assertNotNull(estudianteGuardado);
        assertEquals("Juan", estudianteGuardado.getNombre());
    }

    @Test
    void testGuardarEstudiante_EmailDuplicado() {
        estudianteService.guardarEstudiante(1L, "Juan", "Masculino", 20, "Ingeniería", "juan@example.com", 4, 4.5f);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.guardarEstudiante(2L, "Pedro", "Masculino", 22, "Ingeniería", "juan@example.com", 3, 4.0f);
        });

        String expectedMessage = "El email ya está registrado";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testActualizarEstudiante_Success() {
        estudianteService.guardarEstudiante(1L, "Juan", "Masculino", 20, "Ingeniería", "juan@example.com", 4, 4.5f);
        boolean result = estudianteService.actualizarEstudiante(1L, "Juan Actualizado", "Masculino", 21, "Medicina", "juan2@example.com", 5, 4.8f);
        assertTrue(result);

        EstudianteORM estudianteActualizado = estudianteJPA.findById(1L).orElse(null);
        assertNotNull(estudianteActualizado);
        assertEquals("Juan Actualizado", estudianteActualizado.getNombre());
        assertEquals("juan2@example.com", estudianteActualizado.getEmail());
    }

    @Test
    void testActualizarEstudiante_NoExistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.actualizarEstudiante(99L, "No Existente", "Masculino", 21, "Medicina", "noexiste@example.com", 5, 4.8f);
        });

        String expectedMessage = "El estudiante con el ID proporcionado no existe";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}