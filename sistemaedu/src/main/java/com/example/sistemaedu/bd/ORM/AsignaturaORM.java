package com.example.sistemaedu.bd.ORM;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Table (name = "asignaturas")
@Entity
@Data
@NoArgsConstructor
public class AsignaturaORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombreAsignatura;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private ProfesorORM profesor;

    @ManyToMany
    @JoinTable(
            name = "asignatura_estudiante",
            joinColumns = @JoinColumn(name = "asignatura_id"),
            inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private List<EstudianteORM> estudiantes;

    public AsignaturaORM(Long id, String nombreAsignatura, ProfesorORM profesor, List<EstudianteORM> estudiantes) {
        this.id = id;
        this.nombreAsignatura = nombreAsignatura;
        this.profesor = profesor;
        this.estudiantes = estudiantes;
    }
}
