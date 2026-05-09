package com.helpdesk.helpdesk_api.dtos;

import com.helpdesk.helpdesk_api.enums.Cargo;

public record UsuarioResponse (
        Long id,
        String nome,
        String email,
        Cargo cargo
){}