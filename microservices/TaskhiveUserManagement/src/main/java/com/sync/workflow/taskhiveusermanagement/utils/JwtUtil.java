package com.sync.workflow.taskhiveusermanagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@SuppressWarnings("deprecation")
public class JwtUtil {

    private String SECRET_KEY = "zW0fEmAl96O4sIO7g2v5p7dkqNSURP2HOe0UG/8uL/k="; 
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername()); 
    }


    
	private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(getTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public void cacheToken(String username, String token, long expirationTime) {
        if (username != null) {
            redisTemplate.opsForValue().set(username, token, expirationTime, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalArgumentException("Username must not be null");
        }
    }

    public String getCachedToken(String username) {
        if (username != null) {
            return (String) redisTemplate.opsForValue().get(username);
        } else {
            throw new IllegalArgumentException("Username must not be null");
        }
    }

    public void invalidateToken(String username) {
        if (username != null) {
            redisTemplate.delete(username);
        } else {
            throw new IllegalArgumentException("Username must not be null");
        }
    }

    
    public long getTokenExpirationTime() {
    	return (System.currentTimeMillis() + 1000 * 60);
    }
}

