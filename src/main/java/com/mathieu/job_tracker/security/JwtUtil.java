package com.mathieu.job_tracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

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

}