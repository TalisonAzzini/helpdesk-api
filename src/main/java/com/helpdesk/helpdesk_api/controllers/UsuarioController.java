package com.helpdesk.helpdesk_api.controllers;

import com.helpdesk.helpdesk_api.dtos.UsuarioResponse;
import com.helpdesk.helpdesk_api.models.Usuario;
import com.helpdesk.helpdesk_api.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR')")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR')")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario dadosAtualizados) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dadosAtualizados));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
