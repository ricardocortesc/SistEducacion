package com.example.sistemaedu.bd.ORM;

import com.example.sistemaedu.controller.AsignaturaController;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "profesores")
@Entity
@Data
@NoArgsConstructor
public class ProfesorORM {
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
    private String departamento;

    @Column
    private String cargo;

    @Column
    private String email;


    public ProfesorORM(String nombre, String genero, Integer edad, String departamento, String cargo, String email) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.departamento = departamento;
        this.cargo = cargo;
        this.email = email;
    }
}