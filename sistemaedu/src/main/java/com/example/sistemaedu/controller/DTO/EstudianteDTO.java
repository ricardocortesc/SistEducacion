package com.example.sistemaedu.controller.DTO;

public record EstudianteDTO(Long codigo, String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, Float promedio) {
}
