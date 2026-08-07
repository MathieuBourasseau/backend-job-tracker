package com.mathieu.job_tracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;

// Secret token class
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Generate a signed JWT for a given user id, valid for 24h
    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 24);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    // Verify the token
    public Long extractUserId(String token){

        // Same secret key as the one used to sign the token, needed to verify its signature
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        // Verifies the signature and expiration, then reads the payload
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        // Convert the String id into Long because it's required for DB
        return Long.parseLong(claims.getSubject());
    }

}