package com.example.sistemaedu.controller.DTO;


import java.util.List;

public record AsignaturaDTO(Long id, String nombreAsignatura, ProfesorDTO profesor, List<EstudianteDTO> estudiante) {

}
