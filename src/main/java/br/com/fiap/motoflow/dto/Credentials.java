package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;

public record Credentials(

        @NotBlank(message = "O id é obrigatório")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}
