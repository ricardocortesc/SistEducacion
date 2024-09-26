package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import com.example.sistemaedu.logica.EstudianteService;
import com.example.sistemaedu.logica.ProfesorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class ProfesorIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorJPA profesorJPA;
    @Test
    void testGuardarProfesor_Success() {
        boolean result = profesorService.guardarProfesor(1L, "Miguel", "Masculino", 28, "Ingeniería", "Planta", "humildad@example.com");
        assertTrue(result);

        ProfesorORM profesorGuardado = profesorJPA.findById(1L).orElse(null);
        assertNotNull(profesorGuardado);
        assertEquals("Miguel", profesorGuardado.getNombre());
    }

    @Test
    void testGuardarProfesor_EmailDuplicado() {
        profesorService.guardarProfesor(1L, "Miguel", "Masculino", 28, "Ingeniería", "Planta", "humildad@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            profesorService.guardarProfesor(2L, "Pedro", "Masculino", 22, "Ingeniería", "Planta","humildad@example.com");
        });

        String expectedMessage = "El email ya está registrado";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testActualizarProfesor_Success() {
        profesorService.guardarProfesor(1L, "Miguel", "Masculino", 28, "Ingeniería", "Planta", "humildad@example.com");
        boolean result = profesorService.actualizarProfesor(1L, "Miguel Actualizado", "Masculino", 21, "Medicina", "Catedra","humildad2@example.com");
        assertTrue(result);

        ProfesorORM profesorActualizado = profesorJPA.findById(1L).orElse(null);
        assertNotNull(profesorActualizado);
        assertEquals("Miguel Actualizado", profesorActualizado.getNombre());
        assertEquals("humildad2@example.com", profesorActualizado.getEmail());
    }

    @Test
    void testActualizarProfesor_NoExistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            profesorService.actualizarProfesor(99L, "No Existente", "Masculino", 40, "Medicina", "Ninguno","no@existe.com");
        });

        String expectedMessage = "El profesor con el ID proporcionado no existe";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}