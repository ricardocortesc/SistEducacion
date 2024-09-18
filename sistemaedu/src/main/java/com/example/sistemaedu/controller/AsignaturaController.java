package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AsignaturaController {

    private AsignaturaJPA asignaturaJPA;
    private ProfesorJPA profesorJPA;
    private EstudianteJPA estudianteJPA;

    // Crear asignatura
    @PostMapping(path = "/asignatura")
    public String crearAsignatura(@RequestBody AsignaturaORM asignatura) {
        // Fetch existing entities for profesor and estudiantes
        ProfesorORM profesor = profesorJPA.findById(asignatura.getProfesor().getId()).orElse(null);
        List<EstudianteORM> estudiantes = estudianteJPA.findAllById(asignatura.getEstudiantes().stream().map(EstudianteORM::getId).toList());

        if (profesor != null) {
            asignatura.setProfesor(profesor);
        }
        asignatura.setEstudiantes(estudiantes);

        asignaturaJPA.save(asignatura);
        return "Asignatura creada";
    }

    // Obtener todas las asignaturas
    @GetMapping(path = "/asignaturas")
    public List<AsignaturaORM> obtenerAsignaturas() {
        return asignaturaJPA.findAll();
    }

    // Obtener una asignatura por id
    @GetMapping(path = "/asignatura/{id}")
    public List<AsignaturaORM> obtenerAsignatura(@PathVariable Long id) {
        return asignaturaJPA.findAll();

    }

    // Actualizar una asignatura
    @PutMapping(path = "/asignatura/{id}")
    public String actualizarAsignatura(@PathVariable Long id, @RequestBody AsignaturaORM asignatura) {
        if (asignaturaJPA.existsById(id)) {
            asignatura.setId(id);
            asignaturaJPA.save(asignatura);
            return "Asignatura actualizada";
        } else {
            return "Asignatura no encontrada";
        }
    }

    // Eliminar una asignatura por id
    @DeleteMapping(path = "/asignatura/{id}")
    public String eliminarAsignatura(@PathVariable Long id) {
        if (asignaturaJPA.existsById(id)) {
            asignaturaJPA.deleteById(id);
            return "Asignatura eliminada";
        } else {
            return "Asignatura no encontrada";
        }
    }
}
