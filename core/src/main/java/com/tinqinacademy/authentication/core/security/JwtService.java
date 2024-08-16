package com.tinqinacademy.authentication.core.security;

import com.tinqinacademy.authentication.api.exceptions.customExceptions.InvalidJwtException;
import com.tinqinacademy.authentication.core.utils.JwtBlacklistCacheService;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;



@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;
    private final UserRepository userRepository;
    private final JwtBlacklistCacheService jwtBlacklist;


    public boolean validateJwt (String jwt){
        String id;
        String role;
        try {
            id = extractId(jwt);
            role = extractRole(jwt);
        }
        catch (InvalidJwtException ex){
            return false;
        }

        if(jwtBlacklist.existsInCache(jwt)){return false;}

        Optional<User> user = userRepository.findById(UUID.fromString(id));

        if(user.isEmpty() || !user.get().getRole().toString().equals(role)) {return false;}

        return true;
    }

    public Optional<User> validateJwtAndReturnUser (String jwt){
        String id;
        String role;
        try {
            id = extractId(jwt);
            role = extractRole(jwt);
        }
        catch (InvalidJwtException ex){
            return Optional.empty();
        }

        if(jwtBlacklist.existsInCache(jwt)){return Optional.empty();}

        Optional<User> user = userRepository.findById(UUID.fromString(id));

        if(user.isEmpty() || !user.get().getRole().toString().equals(role)) {return Optional.empty();}

        return user;
    }

    public String extractId(String token) throws InvalidJwtException {
        return extractClaim(token,Claims::getSubject);
    }

    public String extractRole(String token) throws InvalidJwtException {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


    private  <T> T extractClaim(String token, Function<Claims,T> claimsResolver) throws InvalidJwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws InvalidJwtException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e){
            throw new InvalidJwtException();
        }
    }

    public String generateToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getUuid().toString())
                .claim("role",user.getRole().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+(5*60*1000)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}