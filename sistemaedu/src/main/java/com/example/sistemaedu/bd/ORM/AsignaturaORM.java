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
    private String nombre;

    @Column
    private Integer creditos;

    public AsignaturaORM(String nombre, Integer creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
    }
}
