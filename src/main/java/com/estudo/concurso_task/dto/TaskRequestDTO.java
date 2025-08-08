package com.estudo.concurso_task.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO (

       @NotBlank String title,

        @NotBlank String description,

       @NotBlank Long userId


){
}
