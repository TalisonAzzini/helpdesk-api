package com.helpdesk.helpdesk_api.dtos;


import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoRequest(
        @NotBlank(message = "Título obrigatório")
        String titulo,

        @NotBlank(message = "Descrição obrigatória")
        String descricao,

        @NotNull(message = "Prioridade obrigatória")
        Prioridade prioridade,

        @NotNull(message = "Status obrigatório")
        Status status,

        @NotNull(message = "Solicitante obrigatório")
        Long solicitanteId,

        @NotNull(message = "Tecnico obrigatório")
        Long tecnicoId
) {}