package com.kdu.student_management_system.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT Token Provider - Handles JWT token creation, validation, and parsing
 * 
 * + Generates signed JWT tokens
 * + Validates token integrity and expiration
 * + Extracts claims (like username) from tokens
 */
@Component
public class JwtTokenProvider {

    // Secret key for signing tokens (inserted from properties)
    @Value("${jwt.secret}") 
    private String jwtSecret;

    // Token expiration time in milliseconds(inserted from properties)
    @Value("${jwt.expiration}") 
    private long jwtExpiration;

    /**
     * Generates a new JWT token for authenticated users
     * 
     * Token Contents:
     * + Subject (username)
     * + Issued at timestamp
     * + Expiration timestamp
     * + Signed with HS512 algorithm
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Validates a JWT token's integrity and expiration
     * 
     * Checks Performed:
     * + Signature verification
     * + Token expiration
     * + Proper token structure
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Get username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Returns the username
    }
}