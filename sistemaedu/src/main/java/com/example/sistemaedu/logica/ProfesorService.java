package com.example.sistemaedu.logica;

import com.example.sistemaedu.bd.JPA.ProfesorJPA;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfesorService {

    private final ProfesorJPA profesorJPA;
    public boolean guardarProfesor(Long id,String nombre, String genero, Integer edad, String departamento, String cargo, String email){
        if (edad < 0 || edad >100 ){
            throw new IllegalArgumentException("no se permite una edad negativa o mayor a 100");
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

}

