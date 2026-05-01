package com.helpdesk.helpdesk_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.helpdesk.helpdesk_api.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${api.security.token.secret}")
    private String chaveSecreta;

    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(chaveSecreta);

        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("cargo", usuario.getCargo().getRole())
                .withIssuer("helpdesk-api")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(8)))
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