package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.exceptions.OperadorNotFoundException;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.repository.OperadorRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private final Algorithm algorithm;

    private final OperadorRepository usuarioRepository;

    @Autowired
    public TokenService(
            @Value("${jwt.secret}") String secret,
            OperadorRepository usuarioRepository
    ) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("jwt.secret não pode ser nulo ou vazio");
        }

        this.algorithm = Algorithm.HMAC256(secret);
        this.usuarioRepository = usuarioRepository;
    }

    public String createToken(Operador user) {
        Instant expiresAt = LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.ofHours(-3));

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }


    public Operador getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        Long userId = Long.valueOf(jwtVerified.getSubject());

        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new OperadorNotFoundException("Operador não encontrado no banco"));
    }


}
