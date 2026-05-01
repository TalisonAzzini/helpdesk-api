package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.infra.exceptions.UsuarioDuplicadoException;
import com.helpdesk.helpdesk_api.infra.security.JwtService;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public String autenticarLogin(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida!");
        }

        return jwtService.gerarToken(usuario);
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioDuplicadoException();
        }

        usuario.setSenha(encoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }
}