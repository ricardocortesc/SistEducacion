package com.example.sistemaedu.controller;

public record EstudianteDTO(String codigo, String nombre, String genero, Integer edad, String carrera, String email, Integer semestre, Float promedio) {
}
