package com.uoc.tumesa.app.spring.sec.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AntPathRequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/login", "POST"),
            new AntPathRequestMatcher("/registro", "POST"),
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
        /*
        String token = request.getHeader("Authorization");
        jwtService.validateToken(token, "user");
        jwtService.extractUsername(token);
        */
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(requestMatchers)
                .anyMatch(matcher -> matcher.matches(request));
    }
}