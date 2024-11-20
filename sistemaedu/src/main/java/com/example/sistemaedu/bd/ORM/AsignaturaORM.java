package com.example.sistemaedu.bd.ORM;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "asignatura")
@Entity
@Data
@NoArgsConstructor
public class AsignaturaORM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private Integer creditos;

    @ManyToOne(fetch = FetchType.EAGER) // Una asignatura tiene un único profesor
    @JoinColumn(name = "profesor_id", nullable = false) // Clave foránea hacia "profesores"
    private ProfesorORM profesor;

    @ManyToMany(fetch = FetchType.LAZY) // Relación muchos a muchos con estudiantes
    @JoinTable(
            name = "asignatura_estudiante", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "asignatura_id"), // FK hacia asignaturas
            inverseJoinColumns = @JoinColumn(name = "estudiante_id") // FK hacia estudiantes
    )
    private List<EstudianteORM> estudiantes = new ArrayList<>(); // Inicialización de la lista

    // Constructor personalizado

    // Constructor para agregar estudiantes opcionalmente al crear la asignatura
    public AsignaturaORM(String nombre, String descripcion, Integer creditos, ProfesorORM profesor, List<EstudianteORM> estudiantes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.profesor = profesor;
        this.estudiantes = estudiantes != null ? estudiantes : new ArrayList<>(); // Maneja null
    }
}
