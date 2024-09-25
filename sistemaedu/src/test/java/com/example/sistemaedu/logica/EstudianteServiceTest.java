package com.example.sistemaedu.logica;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.logica.EstudianteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    EstudianteJPA estudianteJPA;

    @InjectMocks
    EstudianteService service;

    @Test
    void Given_promedioInvalidoMenorA0_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, -1.0f)
        );
    }

    @Test
    void Given_promedioInvalidoMayorA5_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 6.0f)
        );
    }

    @Test
    void Given_edadInvalidoMenorA0_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", -20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 5.0f)
        );
    }

    @Test
    void Given_edadInvalidoMayorA100_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 101, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 5.0f)
        );
    }

    @Test
    void Given_semestreInvalidoMenorA0_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 101, "Ingenieria de Sistemas", "yarci@gmail.com", -1, 5.0f)
        );
    }

    @Test
    void Given_semestreInvalidoMayorA14_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 101, "Ingenieria de Sistemas", "yarci@gmail.com", 15, 5.0f)
        );
    }

    @Test
    void Given_emailExistente_When_guardarEstudiante_Then_throwIllegalArgumentException() {
        // Arrange
        String emailExistente = "pedro.gomez@correo.com";
        when(estudianteJPA.findByEmail(emailExistente)).thenReturn(Optional.of(new EstudianteORM()));

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 19, "Ingenieria de Sistemas", "pedro.gomez@correo.com", 8, 5.0f)
        );
    }


    @Test
    void When_guardarEstudiante_Then_returnTrue() {
        boolean resultado = service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 18, "Ingenieria de Sistemas", "yarci@gmail.com", 12, 5.0f);
        Assertions.assertTrue(resultado);
        Mockito.verify(estudianteJPA).save(Mockito.any());
    }

    @Test
    void Given_promedioInvalidoMenorA0_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, -1.0f)
        );
    }

    @Test
    void Given_promedioInvalidoMayorA5_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 6.0f)
        );
    }


    @Test
    void Given_edadInvalidoMenorA0_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", -20, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 5.0f)
        );
    }

    @Test
    void Given_edadInvalidoMayorA100_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 101, "Ingenieria de Sistemas", "yarci@gmail.com", 5, 5.0f)
        );
    }

    @Test
    void Given_semestreInvalidoMenorA0_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", -1, 5.0f)
        );
    }

    @Test
    void Given_semestreInvalidoMayorA14_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 20, "Ingenieria de Sistemas", "yarci@gmail.com", 15, 5.0f)
        );
    }

    @Test
    void Given_emailExistente_When_actualizarEstudiante_Then_throwIllegalArgumentException() {
        // Arrange
        String emailExistente = "pedro.gomez@correo.com";
        when(estudianteJPA.findByEmail(emailExistente)).thenReturn(Optional.of(new EstudianteORM()));

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 19, "Ingenieria de Sistemas", "pedro.gomez@correo.com", 8, 5.0f)
        );
    }

    @Test
    void When_actualizarEstudiante_Then_returnTrue() {
        when(estudianteJPA.findById(10L)).thenReturn(Optional.of(new EstudianteORM()));
        boolean resultado = service.actualizarEstudiante(10L, "Plarci Rodriguez", "Masculino", 18, "Ingenieria de Sistemas", "yarci@gmail.com", 12, 5.0f);
        Assertions.assertTrue(resultado);
        Mockito.verify(estudianteJPA).save(Mockito.any());
    }


}

