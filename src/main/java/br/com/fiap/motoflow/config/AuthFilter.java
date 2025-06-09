package br.com.fiap.motoflow.config;

import br.com.fiap.motoflow.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var header = request.getHeader("Authorization");
        // Se não tiver header, apenas segue o fluxo (ex: endpoint público)
        if (header == null || header.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Se tiver header mas não for Bearer, ignora autenticação (não lança 401)
        if (!header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = header.replace("Bearer ", "");

        try {
            var user = tokenService.getUserFromToken(jwt);

            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            Logger.getLogger(AuthFilter.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }

        filterChain.doFilter(request, response);
    }
}
