package com.uoc.tumesa.app.spring.sec.jwt;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio encargado de la validación y generación de tokens JWT de autenticación.
 * */
@Component
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    public String generateToken(String username) {
        Map<String, Object> claims = Maps.newHashMap();
        return createToken(claims, username);
    }

    public Boolean validateToken(String token, String user) {
        final String username = extractUsername(token);
        return username.equals(user) && !isTokenExpired(token);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 300)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
