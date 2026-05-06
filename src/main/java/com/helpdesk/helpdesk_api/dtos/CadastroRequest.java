package com.helpdesk.helpdesk_api.dtos;

import com.helpdesk.helpdesk_api.enums.Cargo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroRequest(
        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        String senha,

        @NotNull(message = "Cargo obrigatório")
        Cargo cargo
) {}