package com.example.sistemaedu.controller.DTO;

import java.util.List;

public record AsignaturaDTO(
        Long id,
        String nombre,
        String descripcion,
        Integer creditos,
        Long profesorId,
        List<Long> estudiantes
) {}
