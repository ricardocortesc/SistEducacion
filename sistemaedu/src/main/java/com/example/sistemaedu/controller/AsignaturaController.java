package com.example.sistemaedu.controller;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.controller.DTO.AsignaturaDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AsignaturaController {

    private AsignaturaJPA asignaturaJPA;

    List<AsignaturaDTO> asignaturas = new ArrayList<>();

    @PostMapping(path = "/asignatura")
    public String guardarAsignatura(@RequestBody AsignaturaDTO asignatura) {
        asignaturas.add(asignatura);
        asignaturaJPA.save(new AsignaturaORM(asignatura.nombre(), asignatura.creditos()));
        return "Asignatura guardada";
    }

    @GetMapping(path = "/asignaturas-bd")
    public List<AsignaturaORM> obtenerAsignaturasBD() {
        return asignaturaJPA.findAll();
    }

    @PutMapping(path = "/asignatura/{id}")
    public String actualizarAsignatura(@PathVariable Long id, @RequestBody AsignaturaDTO asignatura) {
        AsignaturaORM asignaturaExistente = asignaturaJPA.findById(id).orElse(null);
        if (asignaturaExistente != null) {
            asignaturaExistente.setNombre(asignatura.nombre());
            asignaturaExistente.setCreditos(asignatura.creditos());

            // Guardar los cambios en la base de datos
            asignaturaJPA.save(asignaturaExistente);

            return "Asignatura actualizada correctamente";
        } else {
            return "Asignatura no encontrada";
        }
    }

    @GetMapping(path = "/asignaturas/{id}")
    public AsignaturaDTO obtenerAsignatura(@PathVariable Long id) {
        return asignaturaJPA.findById(id)
                .map(asignaturaORM -> new AsignaturaDTO(
                        asignaturaORM.getId(),
                        asignaturaORM.getNombre(),
                        asignaturaORM.getCreditos()
                ))
                .orElse(null);
    }

    @DeleteMapping(path = "/asignaturaEliminada/{id}")
    public String eliminarAsignatura(@PathVariable Long id) {
        asignaturaJPA.deleteById(id);
        return "Asignatura eliminada";
    }
}
