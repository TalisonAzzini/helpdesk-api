package com.helpdesk.helpdesk_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${api.security.token.secret}")
    private String chaveSecreta;

    private final UsuarioRepository usuarioRepository;

    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(chaveSecreta);

        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("cargo", usuario.getCargo().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .sign(algorithm);

    }

    public String validarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(chaveSecreta);

        return JWT.require(algorithm)
                .withIssuer("helpdesk-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
