package com.example.sistemaedu.controller;

public record EstudianteDTO(Long codigo, String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, Float promedio) {
}
