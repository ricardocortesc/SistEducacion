package com.example.sistemaedu;

import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import com.example.sistemaedu.logica.ProfesorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    ProfesorJPA profesorJPA;

    @InjectMocks
    ProfesorService service;

    @Test
    void Given_edadInvalidaMenorA0_When_guardarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarProfesor(3L, "Maigol Rodriguez", "Masculino", -3, "Ingenieria", "Planta", "maigol@gmail.com")
        );
    }

    @Test
    void Given_edadInvalidoMayorA100_When_guardarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarProfesor(3L, "Maigol Rodriguez", "Masculino", 109, "Ingenieria","Planta" ,"maigol@gmail.com" )
        );
    }

    @Test
    void Given_emailExiste_When_guardarProfesor_Then_throwIllegalArgumentException() {
        String emailExistente = "prueba@gmail.com";
        when(profesorJPA.findByEmail(emailExistente)).thenReturn(Optional.of(new ProfesorORM())); // Simula que el email ya existe
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.guardarProfesor(3L, "Maigol Rodriguez", "Masculino", 19, "Ingenieria", "Planta", "prueba@gmail.com")
        );
    }


    @Test
    void When_guardarProfesor_Then_returnTrue() {
        boolean resultado = service.guardarProfesor(3L, "Maigol Rodriguez", "Masculino", 18, "Ingenieria","Planta" ,"maigol@gmail.com" );
        Assertions.assertTrue(resultado);
        Mockito.verify(profesorJPA).save(Mockito.any());
    }

    //Actualizar
    @Test
    void Given_edadInvalidaMenorA0_When_actualizarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarProfesor(3L, "Maigol Rodriguez", "Masculino", -3, "Ingenieria", "Planta", "maigol@gmail.com")
        );
    }

    @Test
    void Given_edadInvalidoMayorA100_When_actualizarProfesor_Then_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarProfesor(3L, "Maigol Rodriguez", "Masculino", 109, "Ingenieria", "Planta", "maigol@gmail.com")
        );
    }

    @Test
    void Given_emailExiste_When_actualizarProfesor_Then_throwIllegalArgumentException() {
        String emailExistente = "prueba@gmail.com";
        when(profesorJPA.findByEmail(emailExistente)).thenReturn(Optional.of(new ProfesorORM())); // Simula que el email ya existe
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.actualizarProfesor(3L, "Maigol Rodriguez", "Masculino", 19, "Ingenieria", "Planta", "prueba@gmail.com")
        );
    }

    @Test
    void When_actualizarProfesor_Then_returnTrue() {
        when(profesorJPA.findById(3L)).thenReturn(Optional.of(new ProfesorORM()));

        boolean resultado = service.actualizarProfesor(3L, "Maigol Rodriguez", "Masculino", 18, "Ingenieria", "Planta", "maigol@gmail.com");
        Assertions.assertTrue(resultado);
        Mockito.verify(profesorJPA).save(Mockito.any());
    }
}
