package com.example.sistemaedu.logica;

import com.example.sistemaedu.bd.JPA.EstudianteJPA;
import com.example.sistemaedu.bd.ORM.EstudianteORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EstudianteService {

    private final EstudianteJPA estudianteJPA;
    public boolean guardarEstudiante(Long id,String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, Float promedio){
        if (promedio < 0 || promedio >5 ){
            throw new IllegalArgumentException("no se permite un promedio mayor a 5 o menor a 0");
        }
        if (edad < 0 || edad >100 ){
            throw new IllegalArgumentException("no se permite esa edad");
        }
        if (semestre < 0 || semestre >14 ){
            throw new IllegalArgumentException("no se pueden tener mas de 14 semestres o menos de 0 semestres");
        }
        if(estudianteJPA.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("El email ya está registrado");
        }

        EstudianteORM nuevoEstudiante =new EstudianteORM();
        nuevoEstudiante.setId(id);
        nuevoEstudiante.setNombre(nombre);
        nuevoEstudiante.setGenero(genero);
        nuevoEstudiante.setEdad(edad);
        nuevoEstudiante.setCarrera(carrera);
        nuevoEstudiante.setEmail(email);
        nuevoEstudiante.setSemestre(semestre);
        nuevoEstudiante.setPromedio(promedio);
        estudianteJPA.save(nuevoEstudiante);
        return true;
    }

    public boolean actualizarEstudiante(Long id, String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, Float promedio) {
        if (promedio < 0 || promedio > 5) {
            throw new IllegalArgumentException("No se permite un promedio mayor a 5 o menor a 0");
        }
        if (edad < 0 || edad > 100) {
            throw new IllegalArgumentException("No se permite esa edad");
        }
        if (semestre < 0 || semestre > 14) {
            throw new IllegalArgumentException("No se permiten más de 14 semestres o menos de 0 semestres");
        }
        if(estudianteJPA.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Optional<EstudianteORM> estudianteExistente = estudianteJPA.findById(id);

        if (estudianteExistente.isPresent()) {
            // Actualizar los campos del estudiante
            EstudianteORM estudiante = estudianteExistente.get();
            estudiante.setNombre(nombre);
            estudiante.setGenero(genero);
            estudiante.setEdad(edad);
            estudiante.setCarrera(carrera);
            estudiante.setEmail(email);
            estudiante.setSemestre(semestre);
            estudiante.setPromedio(promedio);

            // Guardar los cambios en la base de datos
            estudianteJPA.save(estudiante);
            return true;
        } else {
            throw new IllegalArgumentException("El estudiante con el ID proporcionado no existe");
        }
    }


}
