package com.example.blog_api.service.Authen;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class Token {
    @Getter
    private final String token;

    private Token(String token){
        this.token = token;
    }

    public static Token of(Long userId, Long validityMinutes, String secretKey){
        var issueDate = Instant.now();
        return new Token(
                Jwts.builder()
                .claim("user_Id", userId)
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(issueDate.plus(validityMinutes, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact()
        );

    }
}


