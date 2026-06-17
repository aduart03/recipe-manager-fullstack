package com.recipestore.recipe_manager_api.service;

import java.security.Key; // jwt token will need a key
import java.util.Date;  // jwt token will also need a date

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtAuthentication123456789";

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role) {

    return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSignInKey())
            .compact();
}

}
