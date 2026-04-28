package com.helpdesk.helpdesk_api.service;

import com.helpdesk.helpdesk_api.infra.security.JwtService;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }

        return jwtService.gerarToken(usuario);
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));

        usuarioRepository.save(usuario);
    }
}