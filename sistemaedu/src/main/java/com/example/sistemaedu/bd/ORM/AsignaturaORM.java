package com.example.sistemaedu.bd.ORM;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "asignaturas")
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

    @ManyToOne(fetch = FetchType.LAZY) // Una asignatura tiene un único profesor
    @JoinColumn(name = "profesor_id", nullable = false) // Clave foránea hacia "profesores"
    private ProfesorORM profesor;

    @ManyToMany(fetch = FetchType.LAZY) // Relación muchos a muchos con estudiantes
    @JoinTable(
            name = "asignaturas_estudiantes", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "asignatura_id"), // FK hacia asignaturas
            inverseJoinColumns = @JoinColumn(name = "estudiante_id") // FK hacia estudiantes
    )
    private List<EstudianteORM> estudiantes; // Lista de estudiantes inscritos

    public AsignaturaORM(String nombre, String descripcion, ProfesorORM profesor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.profesor = profesor;
    }
}
