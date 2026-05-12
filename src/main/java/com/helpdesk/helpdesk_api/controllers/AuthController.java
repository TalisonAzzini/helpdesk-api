package com.helpdesk.helpdesk_api.controllers;

import com.helpdesk.helpdesk_api.dtos.CadastroRequest;
import com.helpdesk.helpdesk_api.dtos.LoginRequest;
import com.helpdesk.helpdesk_api.dtos.LoginResponse;
import com.helpdesk.helpdesk_api.dtos.UsuarioResponse;
import com.helpdesk.helpdesk_api.models.Usuario;
import com.helpdesk.helpdesk_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest dadosLogin) {
        String token = authService.autenticarLogin(dadosLogin.email(), dadosLogin.senha());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid CadastroRequest dadosCadastro) {
        Usuario usuario = new Usuario();
        usuario.setNome(dadosCadastro.nome());
        usuario.setEmail(dadosCadastro.email());
        usuario.setSenha(dadosCadastro.senha());
        usuario.setCargo(dadosCadastro.cargo());

        Usuario usuarioSalvo = authService.cadastrarUsuario(usuario);

        return ResponseEntity.status(201).body(
                new UsuarioResponse(
                        usuarioSalvo.getId(),
                        usuarioSalvo.getNome(),
                        usuarioSalvo.getEmail(),
                        usuarioSalvo.getCargo()
                ));
    }
}