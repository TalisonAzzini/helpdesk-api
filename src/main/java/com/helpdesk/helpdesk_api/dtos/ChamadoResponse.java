package com.helpdesk.helpdesk_api.dtos;

import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.enums.Status;
import com.helpdesk.helpdesk_api.models.Chamado;

import java.time.LocalDateTime;

public record ChamadoResponse(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        Status status,
        LocalDateTime dataCriado,
        LocalDateTime dataFechado,
        UsuarioResponse solicitante,
        UsuarioResponse tecnico
) {
    public static ChamadoResponse from(Chamado chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getDataCriado(),
                chamado.getDataFechado(),
                UsuarioResponse.from(chamado.getSolicitante()),
                UsuarioResponse.from(chamado.getTecnico())
        );
    }
}