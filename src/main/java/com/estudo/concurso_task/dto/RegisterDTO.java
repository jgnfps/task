package com.estudo.concurso_task.dto;

import com.estudo.concurso_task.enuns.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
