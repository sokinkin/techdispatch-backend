package com.techdispatch.my_springboot_platform.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techdispatch.my_springboot_platform.Models.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Signs and verifies the JSON Web Tokens we hand out at login. The token carries
// the user's email (subject), their role, and the linked customer/technician id.
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(Account account) {
        Date now = new Date();
        return Jwts.builder()
                .subject(account.getEmail())
                .claim("role", account.getRole().name())
                .claim("accountId", account.getId())
                .claim("customerId", account.getCustomerId())
                .claim("technicianId", account.getTechnicianId())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    // Verifies the signature + expiry and returns the claims. Throws if invalid.
    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
