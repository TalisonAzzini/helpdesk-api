package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.infra.exceptions.UsuarioDuplicadoException;
import com.helpdesk.helpdesk_api.infra.security.JwtService;
import com.helpdesk.helpdesk_api.models.Usuario;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public String autenticarLogin(String email, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

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