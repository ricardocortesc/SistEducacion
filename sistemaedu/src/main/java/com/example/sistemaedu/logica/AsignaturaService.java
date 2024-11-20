package com.example.sistemaedu.logica;

import com.example.sistemaedu.bd.JPA.AsignaturaJPA;
import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AsignaturaService {

    private final AsignaturaJPA asignaturaJPA;
    private final EstudianteJPA estudianteJPA;
    private final ProfesorJPA profesorJPA;

    public AsignaturaORM guardarAsignatura(String nombre, String descripcion, Integer creditos, ProfesorORM profesor, List<EstudianteORM> estudiantes) {
        // Crear la asignatura
        AsignaturaORM asignatura = new AsignaturaORM(nombre, descripcion, creditos, profesor, estudiantes);

        // Guardar la asignatura con los estudiantes asociados
        return asignaturaJPA.save(asignatura);
    }
}

