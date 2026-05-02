package com.helpdesk.helpdesk_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email Obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha Obrigatória")
        String senha
) {}