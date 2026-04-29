package com.helpdesk.helpdesk_api.controller;

import com.helpdesk.helpdesk_api.model.Login;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.service.AuthService;
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
    public ResponseEntity<String> login(@RequestBody Login dadosLogin) {
        return ResponseEntity.status(201).body(authService.autenticarLogin(dadosLogin.email(), dadosLogin.senha()));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(201).body(authService.cadastrarUsuario(usuario));
    }
}
