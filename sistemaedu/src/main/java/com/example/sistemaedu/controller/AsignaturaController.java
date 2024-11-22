package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import com.example.sistemaedu.controller.DTO.AsignaturaDTO;
import com.example.sistemaedu.eventos.AssignmentEvent;
import com.example.sistemaedu.eventos.RabbitMQProducer;
import com.example.sistemaedu.logica.AsignaturaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AsignaturaController {

    private final AsignaturaJPA asignaturaJPA;
    private final ProfesorJPA profesorJPA;
    private final EstudianteJPA estudianteJPA;
    private final AsignaturaService asignaturaService;
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping(path = "/asignatura")
    public String guardarAsignatura(@RequestBody AsignaturaDTO asignaturaDTO) {
        // Buscar al profesor
        ProfesorORM profesor = profesorJPA.findById(asignaturaDTO.profesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        // Convertir la lista de IDs en una lista de EstudianteORM
        List<EstudianteORM> estudiantes = asignaturaDTO.estudiantes().stream()
                .map(id -> estudianteJPA.findById(id)
                        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id)))
                .toList();

        // Crear la asignatura con los estudiantes
        AsignaturaORM asignatura = asignaturaService.guardarAsignatura(
                asignaturaDTO.nombre(),
                asignaturaDTO.descripcion(),
                asignaturaDTO.creditos(),
                profesor,
                estudiantes
        );
        estudiantes.forEach(estudiante -> {
            AssignmentEvent event = new AssignmentEvent(
                    asignatura.getNombre(),
                    asignatura.getId(),
                    estudiante.getNombre(),
                    estudiante.getId(),
                    profesor.getNombre(),
                    LocalDateTime.now()
            );
            rabbitMQProducer.sendAssignmentEvent(event);
        });

        return "Asignatura guardada con estudiantes";
    }

    @GetMapping(path = "/asignaturas")
    public List<AsignaturaORM> obtenerAsignaturas() {
        return asignaturaJPA.findAll();
    }

    @GetMapping(path = "/asignatura/{id}")
    public AsignaturaDTO obtenerAsignatura(@PathVariable Long id) {
        AsignaturaORM asignatura = asignaturaJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        return new AsignaturaDTO(
                asignatura.getId(),
                asignatura.getNombre(),
                asignatura.getDescripcion(),
                asignatura.getCreditos(),
                asignatura.getProfesor().getId(),
                asignatura.getEstudiantes().stream().map(EstudianteORM::getId).toList()
        );
    }

    @DeleteMapping(path = "/asignaturaEliminada/{id}")
    public String eliminarAsignatura(@PathVariable Long id) {
        asignaturaJPA.deleteById(id);
        return "Asignatura eliminada";
    }
}
