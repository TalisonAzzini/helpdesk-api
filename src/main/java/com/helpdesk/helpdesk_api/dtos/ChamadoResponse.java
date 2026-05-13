package com.helpdesk.helpdesk_api.dtos;

import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.enums.Status;
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
) {}