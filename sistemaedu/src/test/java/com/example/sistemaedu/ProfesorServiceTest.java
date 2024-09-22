package com.example.sistemaedu;

import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.logica.ProfesorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    ProfesorJPA ProfesorJPA;

    @InjectMocks
    ProfesorService service;

    @Test
    void Given_edadInvalidaMenorA0_When_guardarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarProfesor(10L, "Maigol Rodriguez", "Masculino", 20, "Ingenieria", "Planta", "maigol@gmail.com")
        );
    }

    @Test
    void Given_edadInvalidoMayorA100_When_guardarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarProfesor(10L, "Maigol Rodriguez", "Masculino", 20, "Ingenieria","Planta" ,"maigol@gmail.com" )
        );
    }

    @Test
    void When_guardarEstudiante_Then_returnTrue() {
        boolean resultado = service.guardarProfesor(10L, "Maigol Rodriguez", "Masculino", 18, "Ingenieria","Planta" ,"maigol@gmail.com" );
        Assertions.assertTrue(resultado);
        Mockito.verify(ProfesorJPA).save(Mockito.any());
    }
}
