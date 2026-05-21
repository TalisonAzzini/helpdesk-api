package com.helpdesk.helpdesk_api.dtos;

import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.models.Usuario;

public record UsuarioResponse (
        Long id,
        String nome,
        String email,
        Cargo cargo
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCargo()
        );
    }
}