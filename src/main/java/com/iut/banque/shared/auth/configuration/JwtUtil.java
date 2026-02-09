package com.iut.banque.shared.auth.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final Key key; // Clé secrète pour la signature des tokens
    private final long authExpiration; // Durée d'expiration des tokens d'authentification
    private final long resetExpiration; // Durée d'expiration des tokens de réinitialisation

    public JwtUtil(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration.auth}") long authExpiration,
            @Value("${jwt.expiration.reset}") long resetExpiration) {

        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalArgumentException(
                    "JWT_SECRET n'est pas défini dans les variables d'environnement");
        }

        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authExpiration = authExpiration;
        this.resetExpiration = resetExpiration;
    }

    public String generateAuthToken(String username, List<String> roles) {
        return generateToken(username, roles, authExpiration);
    }

    public String generateResetToken(String username) {
        return generateToken(username, null, resetExpiration);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token) != null
                ? Objects.requireNonNull(extractAllClaims(token)).getSubject()
                : null;
    }

    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.get("roles", List.class) : null;
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private String generateToken(String username, List<String> roles, long expiration) {
        JwtBuilder builder =
                Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(key, SignatureAlgorithm.HS256);

        if (roles != null) {
            builder.claim("roles", roles);
        }

        return builder.compact();
    }

    private Claims extractAllClaims(String token) {
        if (validateToken(token)) {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } else {
            return null;
        }
    }
}