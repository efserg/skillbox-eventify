package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.InvalidJwtAuthenticationException;
import com.skillbox.eventify.schema.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements com.skillbox.eventify.service.JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @Override
    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", List.of(user.getRole().name()));
        claims.put("userId", user.getId());

        Instant now = Instant.now();
        Instant validity = now.plusSeconds(validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now.getEpochSecond() * 1000L))
                .setExpiration(new Date(validity.getEpochSecond() * 1000L))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException();
        }
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
