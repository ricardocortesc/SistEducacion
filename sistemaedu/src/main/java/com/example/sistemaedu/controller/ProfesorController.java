package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.ORM.ProfesorORM;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;

import com.example.sistemaedu.controller.DTO.ProfesorDTO;
import com.example.sistemaedu.logica.ProfesorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("https://sistemaedufront.onrender.com")
public class ProfesorController {

    private ProfesorJPA profesorJPA;

    private ProfesorService profesorService;
    List<ProfesorDTO> profesores = new ArrayList<>();


    @PostMapping(path = "/profesor")
    public String guardarProfesor(@RequestBody ProfesorDTO profesor) {
        profesores.add(profesor);
        profesorService.guardarProfesor(profesor.codigo(), profesor.nombre(), profesor.genero(), profesor.edad(), profesor.departamento(), profesor.cargo(), profesor.email());
        return "Profesor guardado";
    }
    @GetMapping(path = "/profesores-bd")
    public List<ProfesorORM> obtenerProfesoresBD() {
        return profesorJPA.findAll();
    }

    @PutMapping(path = "/profesor/{id}")
    public String actualizarProfesor(@PathVariable Long id, @RequestBody ProfesorDTO profesor){
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

    @GetMapping(path = "/profesores/{id}")
    public ProfesorDTO obtenerProfesor(@PathVariable Long id) {
        return profesorJPA.findById(id)
                .map(profesorORM -> new ProfesorDTO(profesorORM.getId(), profesorORM.getNombre(), profesorORM.getGenero(), profesorORM.getEdad(), profesorORM.getDepartamento(), profesorORM.getCargo(), profesorORM.getEmail()))
                .orElse(null);
    }
    @DeleteMapping(path = "/profesorEliminado/{id}")
    public String eliminarProfesor(@PathVariable Long id) {
        profesorJPA.deleteById(id);
        return "Profesor eliminado";
    }

}
