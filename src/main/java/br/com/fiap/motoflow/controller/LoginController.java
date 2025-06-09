package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.Credentials;
import br.com.fiap.motoflow.dto.Token;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.AuthService;
import br.com.fiap.motoflow.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Endpoint para autenticação e geração de token JWT")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Autenticar operador", description = "Retorna um token JWT válido se as credenciais estiverem corretas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token JWT", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Username não encontrado", content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    public Token login(@RequestBody @Valid Credentials credentials) {

        Operador user = (Operador) authService.loadUserByUsername(credentials.username());

        if (!passwordEncoder.matches(credentials.password(), user.getSenha())) {
            throw new BadCredentialsException("Senha incorreta");
        }

        String jwt = tokenService.createToken(user);
        return new Token(jwt);
    }


}
