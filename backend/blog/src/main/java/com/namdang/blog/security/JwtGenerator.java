package com.namdang.blog.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtGenerator {

    @Value("${application.security.jwt.JWT_EXPIRATION}")
    private Long JWT_EXPIRATION;

    private final Key key;

    public JwtGenerator(@Value("${application.security.jwt.SECRET_KEY}") String secretKey) {
        try {
            byte[] decodedKey = hexStringToByteArray(secretKey);
            this.key = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode SecretKey. Ensure it is a valid hexadecimal string.", e);
            throw e;
        }
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))
                .filter(authority -> authority.equals("ADMIN") || authority.equals("TEACHER") || authority.equals("STUDENT"))
                .findFirst()
                .orElse("USER");

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (SecurityException ex) {
            log.error("JWT security exception: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Exception while validating JWT: {}", ex.getMessage());
        }
        return false;
    }
}