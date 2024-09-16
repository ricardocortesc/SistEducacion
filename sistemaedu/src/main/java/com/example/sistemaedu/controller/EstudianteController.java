package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.EstudianteJPA;
import com.example.sistemaedu.bd.EstudianteORM;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class EstudianteController {

    private EstudianteJPA estudianteJPA;

    List<EstudianteDTO> estudiantes = new ArrayList<>();

    @GetMapping(path = "/saludo")
    public String saludar() {
        return "Hola mundo";
    }


    @PostMapping(path = "/estudiante")
    public String guardarEstudiante(@RequestBody EstudianteDTO estudiante) {
        estudiantes.add(estudiante);
        estudianteJPA.save(new EstudianteORM(estudiante.nombre(), estudiante.genero(), estudiante.edad(),estudiante.carrera(), estudiante.email(), estudiante.semestre(), estudiante.promedio()));
        return "Estudiante guardado";
    }
    @GetMapping(path = "/estudiantes-bd")
    public List<EstudianteORM> obtenerEstudiantesBD() {
        return estudianteJPA.findAll();
    }

    @GetMapping(path = "/estudiantes/{id}")
    public EstudianteDTO obtenerEstudiante(@PathVariable Long id) {
        return estudianteJPA.findById(id)
                .map(estudianteORM -> new EstudianteDTO(estudianteORM.getId(), estudianteORM.getNombre(), estudianteORM.getGenero(), estudianteORM.getEdad(), estudianteORM.getCarrera(), estudianteORM.getEmail(), estudianteORM.getSemestre(), estudianteORM.getPromedio()))
                .orElse(null);
    }


    @PutMapping(path = "/estudiante/{id}")
    public String actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudiante){
        EstudianteORM estudianteExistente = estudianteJPA.findById(id).orElse(null);
        if (estudianteExistente != null){
            estudianteExistente.setNombre(estudiante.nombre());
            estudianteExistente.setGenero(estudiante.genero());
            estudianteExistente.setEdad(estudiante.edad());
            estudianteExistente.setCarrera(estudiante.carrera());
            estudianteExistente.setEmail(estudiante.email());
            estudianteExistente.setSemestre(estudiante.semestre());
            estudianteExistente.setPromedio(estudiante.promedio());

            // Guardar los cambios en la base de datos
            estudianteJPA.save(estudianteExistente);

            return "Estudiante actualizado correctamente";
        } else {
            return "Estudiante no encontrado";
        }
    }
    @DeleteMapping(path = "/estudianteEliminado/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        estudianteJPA.deleteById(id);
        return "Estudiante eliminado";
    }

}
