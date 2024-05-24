package com.uoc.tumesa.app.spring.sec.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Filtro de Spring Boot invocado al inicio de cada petición para validar (sólo en las peticiones que requieran autenticación)
 * que se haya indicado un token JWT válido (token obtenido en el momento de autenticarse).
 * */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    // URLs que no requieren de autenticación JWT
    private final AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/auth/login"),
            new AntPathRequestMatcher("/auth/registro"),
            new AntPathRequestMatcher("/restaurantes"),
            new AntPathRequestMatcher("/restaurantes/restaurante"),
            new AntPathRequestMatcher("/api/**"),
            new AntPathRequestMatcher("/css/**"),
            new AntPathRequestMatcher("/js/**"),
            new AntPathRequestMatcher("/images/**"),
            new AntPathRequestMatcher("/fonts/**")
    };

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (!validate(request))
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido");

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(requestMatchers)
                .anyMatch(matcher -> matcher.matches(request));
    }

    private boolean validate(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            String username = jwtService.extractUsername(token);

            return jwtService.validateToken(token, username);

        } catch (Exception e) {
            logger.error("Se produjo un error al validar el token JWT", e);
            return false;
        }
    }
}