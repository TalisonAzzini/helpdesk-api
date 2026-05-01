package com.helpdesk.helpdesk_api.controllers;

import com.helpdesk.helpdesk_api.dtos.LoginRequest;
import com.helpdesk.helpdesk_api.dtos.LoginResponse;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dadosLogin) {
        String token = authService.autenticarLogin(dadosLogin.email(), dadosLogin.senha());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(authService.cadastrarUsuario(usuario));
    }
}
