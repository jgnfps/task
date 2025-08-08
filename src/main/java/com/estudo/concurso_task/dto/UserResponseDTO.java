package com.estudo.concurso_task.dto;

public record UserResponseDTO(
        Long id,
        String username,
        String fullName,
        String email
) {}
