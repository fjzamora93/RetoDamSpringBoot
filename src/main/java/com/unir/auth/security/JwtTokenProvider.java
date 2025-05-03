package com.unir.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret;

    private final long accessTokenExpirationMs = 900_000;       // 15 minutos
    private final long refreshTokenExpirationMs = 604_800_000;  // 7 días

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generar Access Token
    public String createAccessToken(String email) {
        return buildToken(email, accessTokenExpirationMs);
    }

    // Generar Refresh Token
    public String createRefreshToken(String email) {
        return buildToken(email, refreshTokenExpirationMs);
    }

    // Construcción del token
    private String buildToken(String email, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener email del token
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validar token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ Token inválido: " + e.getMessage());
            return false;
        }
    }

    // Fecha de expiración
    public Date getExpirationDateFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
}
