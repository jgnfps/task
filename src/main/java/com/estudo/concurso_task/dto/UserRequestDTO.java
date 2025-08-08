package com.estudo.concurso_task.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        String fullName,
        String email
) {}
