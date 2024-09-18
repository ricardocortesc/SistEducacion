package com.example.sistemaedu.bd.ORM;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "estudiante")
@Entity
@Data
@NoArgsConstructor
public class EstudianteORM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String genero;

    @Column
    private Integer edad;
    @Column
    private String carrera;

    @Column
    private String email;

    @Column
    private Integer semestre;

    @Column
    private float promedio;

    @ManyToMany(mappedBy = "estudiantes")
    private List<AsignaturaORM> asignaturas;

    public EstudianteORM( String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, float promedio) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.carrera = carrera;
        this.email = email;
        this.semestre = semestre;
        this.promedio = promedio;
    }

}