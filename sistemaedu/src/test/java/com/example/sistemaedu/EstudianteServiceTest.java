package com.example.sistemaedu;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.logica.EstudianteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    EstudianteJPA EstudianteJPA;

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
    void When_guardarEstudiante_Then_returnTrue() {
        boolean resultado = service.guardarEstudiante(10L, "Plarci Rodriguez", "Masculino", 18, "Ingenieria de Sistemas", "yarci@gmail.com", 12, 5.0f);
        Assertions.assertTrue(resultado);
        Mockito.verify(EstudianteJPA).save(Mockito.any());
    }
}
