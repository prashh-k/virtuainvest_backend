package com.example.virtuainvest_backend.virtuainvest_backend.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;




@Component
public class JwtUtil {
    private final String SECRET_KEY = "yourSecretKey";
    private final long EXPIRATION_TIME = 86400000; // 24 hours

    // Method to generate a secure key from Base64 string
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // ✅ Returns SecretKey instead of Key
    }

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Updated signature method
                .compact();
    }

    // Extract username from JWT Token
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // ✅ Now passing a SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
}



}