package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.ProfesorORM;
import com.example.sistemaedu.bd.ProfesorJPA;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ProfesorController {

    private ProfesorJPA profesorJPA;

    List<ProfesorDTO> profesores = new ArrayList<>();


    @PostMapping(path = "/profesor")
    public String guardarProfesor(@RequestBody ProfesorDTO profesor) {
        profesores.add(profesor);
        profesorJPA.save(new ProfesorORM(profesor.nombre(), profesor.genero(), profesor.edad(),profesor.departamento(), profesor.cargo(), profesor.email()));
        return "Profesor guardado";
    }
    @GetMapping(path = "/profesores-bd")
    public List<ProfesorORM> obtenerEstudiantesBD() {
        return profesorJPA.findAll();
    }

    @PutMapping(path = "/profesor/{id}")
    public String actualizarEstudiante(@PathVariable Long id, @RequestBody ProfesorDTO profesor){
        ProfesorORM profesorExistente= profesorJPA.findById(id).orElse(null);
        if (profesorExistente != null){
            profesorExistente.setNombre(profesor.nombre());
            profesorExistente.setGenero(profesor.genero());
            profesorExistente.setEdad(profesor.edad());
            profesorExistente.setDepartamento(profesor.departamento());
            profesorExistente.setCargo(profesor.cargo());
            profesorExistente.setEmail(profesor.email());


            // Guardar los cambios en la base de datos
            profesorJPA.save(profesorExistente);

            return "Profesor actualizado correctamente";
        } else {
            return "Profesor no encontrado";
        }
    }
    @DeleteMapping(path = "/profesorEliminado/{id}")
    public String eliminarProfesor(@PathVariable Long id) {
        profesorJPA.deleteById(id);
        return "Profesor eliminado";
    }

}
