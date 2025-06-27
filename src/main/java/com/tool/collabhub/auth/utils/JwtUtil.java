package com.tool.collabhub.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET;

    @Value("${jwt.access_token.expiry_in_ms}")
    private long ACCESS_TOKEN_EXPIRY_MS;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(SECRET);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateAccessToken(String userId, String email) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(email)
                .withClaim("userId", userId)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + ACCESS_TOKEN_EXPIRY_MS))
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String extractEmail(String token) {
        try {
            return decodeToken(token).getSubject();
        } catch (JWTVerificationException e) {
            log.warn("Failed to extract email from token: {}", e.getMessage());
            return null; // or throw a custom exception
        }
    }

    public String extractUserId(String token) {
        try {
            return decodeToken(token).getClaim("userId").asString();
        } catch (JWTVerificationException e) {
            log.warn("Failed to extract userId from token: {}", e.getMessage());
            return null; // or throw a custom exception
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiry = decodeToken(token).getExpiresAt();
            boolean expired = expiry.before(new Date());
            if (expired) {
                log.info("Token has expired at: {}", expiry);
            }
            return expired;
        } catch (TokenExpiredException e) {
            log.info(e.getMessage());
            return true;
        } catch (JWTVerificationException e) {
            log.warn("Token verification failed (treating as expired): {}", e.getMessage());
            return true;
        }
    }

    private DecodedJWT decodeToken(String token) {
        return verifier.verify(token);
    }
}