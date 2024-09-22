package com.example.sistemaedu.logica;

import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfesorService {

    private final ProfesorJPA profesorJPA;
    public boolean guardarProfesor(Long id,String nombre, String genero, Integer edad, String departamento, String cargo, String email){
        if (edad < 0 || edad >100 ){
            throw new IllegalArgumentException("no se permite una edad negativa o mayor a 100");
        }
        if(profesorJPA.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("El email ya está registrado");
        }
        ProfesorORM nuevoProfesor =new ProfesorORM();
        nuevoProfesor.setId(id);
        nuevoProfesor.setNombre(nombre);
        nuevoProfesor.setGenero(genero);
        nuevoProfesor.setEdad(edad);
        nuevoProfesor.setDepartamento(departamento);
        nuevoProfesor.setCargo(cargo);
        nuevoProfesor.setEmail(email);
        profesorJPA.save(nuevoProfesor);
        return true;
    }

    public boolean actualizarProfesor(Long id,String nombre, String genero, Integer edad, String departamento, String cargo, String email){
        if (edad < 0 || edad >100 ){
            throw new IllegalArgumentException("no se permite una edad negativa o mayor a 100");
        }
        if(profesorJPA.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Optional<ProfesorORM> profesorExistente = profesorJPA.findById(id);

        if (profesorExistente.isPresent()) {
            // Actualiza los campos del profesor
            ProfesorORM profesor = profesorExistente.get();
            profesor.setNombre(nombre);
            profesor.setGenero(genero);
            profesor.setEdad(edad);
            profesor.setDepartamento(departamento);
            profesor.setCargo(cargo);
            profesor.setEmail(email);

            // Guarda los cambios en la base de datos
            profesorJPA.save(profesor);
            return true;
        } else {
            throw new IllegalArgumentException("El profesor con el ID proporcionado no existe");
        }
    }

}

