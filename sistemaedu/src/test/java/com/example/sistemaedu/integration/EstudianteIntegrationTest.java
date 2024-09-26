package com.example.sistemaedu.integration;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.controller.DTO.EstudianteDTO;
import com.example.sistemaedu.logica.EstudianteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@Transactional
public class EstudianteIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EstudianteService estudianteService;

    @BeforeEach
    void limpiarBaseDeDatos() {
        estudianteJPA.deleteAll();
    }
    @Autowired
    private EstudianteJPA estudianteJPA;
    @Test
    void When_guardarEstudiante_Then_returnEstudianteGuardado() {
        // Crear un objeto EstudianteDTO para la inserción
        EstudianteDTO nuevoEstudiante = new EstudianteDTO(1L, "Juan Perez", "Masculino", 20, "Ingeniería de Sistemas", "work@example.com", 3, 4.5f);

        // Realizar la inserción mediante el endpoint
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/estudiante", nuevoEstudiante, String.class);
        System.out.println(respuestaInsercion);
        Assertions.assertEquals("Estudiante guardado", respuestaInsercion.getBody());

        // Consultar la lista de estudiantes para verificar que se haya guardado correctamente
        ResponseEntity<List> resultado = testRestTemplate.getForEntity("/estudiantes-bd", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultado.getBody()).isEmpty());
        Assertions.assertTrue(resultado.getStatusCode().is2xxSuccessful());

        ResponseEntity<EstudianteDTO> respuestaObtenerEstudiante = testRestTemplate.getForEntity("/estudiantes/1", EstudianteDTO.class);

        // Verifica que la respuesta sea exitosa y que el estudiante devuelto sea el correcto
        Assertions.assertTrue(respuestaObtenerEstudiante.getStatusCode().is2xxSuccessful());
        EstudianteDTO estudianteObtenido = respuestaObtenerEstudiante.getBody();
        Assertions.assertNotNull(estudianteObtenido);
        Assertions.assertEquals(nuevoEstudiante.codigo(), estudianteObtenido.codigo());
        Assertions.assertEquals(nuevoEstudiante.nombre(), estudianteObtenido.nombre());
        Assertions.assertEquals(nuevoEstudiante.genero(), estudianteObtenido.genero());
        Assertions.assertEquals(nuevoEstudiante.edad(), estudianteObtenido.edad());
        Assertions.assertEquals(nuevoEstudiante.carrera(), estudianteObtenido.carrera());
        Assertions.assertEquals(nuevoEstudiante.email(), estudianteObtenido.email());
        Assertions.assertEquals(nuevoEstudiante.semestre(), estudianteObtenido.semestre());
        Assertions.assertEquals(nuevoEstudiante.promedio(), estudianteObtenido.promedio());
    }

    @Test
    void When_obtenerEstudiantePorId_Then_returnEstudiante() {
        // Primero, crea y guarda un nuevo estudiante para que exista en la base de datos
        EstudianteDTO estudianteBuscar = new EstudianteDTO(2L, "Juan Perez", "Masculino", 20, "Ingeniería de Sistemas", "worke@example.com", 3, 4.5f);

        // Realizar la inserción mediante el endpoint
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/estudiante", estudianteBuscar, String.class);
        Assertions.assertEquals("Estudiante guardado", respuestaInsercion.getBody());

        // Ahora, intenta obtener el estudiante por su ID
        ResponseEntity<EstudianteDTO> respuestaObtenerEstudiante = testRestTemplate.getForEntity("/estudiantes/1", EstudianteDTO.class);

        // Verifica que la respuesta sea exitosa y que el estudiante devuelto sea el correcto
        Assertions.assertTrue(respuestaObtenerEstudiante.getStatusCode().is2xxSuccessful());
        EstudianteDTO estudianteObtenido = respuestaObtenerEstudiante.getBody();
        Assertions.assertNotNull(estudianteObtenido);
        Assertions.assertEquals(estudianteBuscar.codigo(), estudianteObtenido.codigo());
        Assertions.assertEquals(estudianteBuscar.nombre(), estudianteObtenido.nombre());
        Assertions.assertEquals(estudianteBuscar.genero(), estudianteObtenido.genero());
        Assertions.assertEquals(estudianteBuscar.edad(), estudianteObtenido.edad());
        Assertions.assertEquals(estudianteBuscar.carrera(), estudianteObtenido.carrera());
        Assertions.assertEquals(estudianteBuscar.email(), estudianteObtenido.email());
        Assertions.assertEquals(estudianteBuscar.semestre(), estudianteObtenido.semestre());
        Assertions.assertEquals(estudianteBuscar.promedio(), estudianteObtenido.promedio());
    }

    @Test
    void When_actualizarEstudiante_Then_returnEstudianteActualizado() {
        // Crear y guardar un estudiante inicial para poder actualizarlo
        EstudianteDTO estudianteInicial = new EstudianteDTO(3L, "Juan Perez", "Masculino", 20, "Ingeniería de Sistemas", "juan.perez@example.com", 3, 4.5f);
        testRestTemplate.postForEntity("/estudiante", estudianteInicial, String.class);

        // Crear un nuevo objeto EstudianteDTO con los datos actualizados
        EstudianteDTO estudianteActualizado = new EstudianteDTO(3L, "Juan Perez", "Masculino", 21, "Ingeniería de Sistemas", "juan.perez@nuevoemail.com", 4, 4.7f);

        // Realizar la actualización mediante el endpoint
        ResponseEntity<String> respuestaActualizacion = testRestTemplate.exchange("/estudiante/1", HttpMethod.PUT, new HttpEntity<>(estudianteActualizado), String.class);

        // Verificar que la respuesta sea exitosa y el mensaje correcto
        Assertions.assertEquals("Estudiante actualizado correctamente", respuestaActualizacion.getBody());

        // Verificar que los datos se hayan actualizado correctamente
        ResponseEntity<EstudianteDTO> resultadoConsulta = testRestTemplate.getForEntity("/estudiantes/1", EstudianteDTO.class);
        EstudianteDTO estudianteConsultado = resultadoConsulta.getBody();

        Assertions.assertNotNull(estudianteConsultado);
        Assertions.assertEquals(21, estudianteConsultado.edad());
        Assertions.assertEquals("juan.perez@nuevoemail.com", estudianteConsultado.email());
    }

    @Test
    void When_actualizarEstudiante_NonExistentId_Then_returnEstudianteNoEncontrado() {
        // Crear un objeto EstudianteDTO para la actualización
        EstudianteDTO estudianteActualizado = new EstudianteDTO(99L, "No Existe", "Desconocido", 25, "Carrera Inexistente", "noexiste@example.com", 5, 3.0f);

        // Realizar la actualización en un id que no existe
        ResponseEntity<String> respuestaActualizacion = testRestTemplate.exchange("/estudiante/99", HttpMethod.PUT, new HttpEntity<>(estudianteActualizado), String.class);

        // Verificar que se reciba el mensaje de estudiante no encontrado
        Assertions.assertEquals("Estudiante no encontrado", respuestaActualizacion.getBody());
    }

    @Test
    void When_eliminarEstudiante_Then_estudianteEliminado() {
        // Eliminar el estudiante mediante el endpoint DELETE
        testRestTemplate.delete("/estudianteEliminado/1");

        // Verificar que el estudiante haya sido eliminado intentando obtenerlo
        EstudianteDTO estudianteEliminado = testRestTemplate.getForObject("/estudiantes/1", EstudianteDTO.class);

        // Asegurarse de que el estudiante no se encuentre (debería ser null)
        Assertions.assertNull(estudianteEliminado);
    }







}