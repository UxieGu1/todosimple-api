package dev.gui.todosimple.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;


@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(String username) {
        SecretKey key = getSecretKey();
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(key)
                .compact();
    }


    private SecretKey getSecretKey(){
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        return secretKey;
    };

    public boolean isValidToken(String token) {
        Claims claims = getClaimsFromToken(token);

        if(Objects.nonNull(claims)){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)){
                return true;
            }
        }
        return false;
    }

    private Claims getClaimsFromToken(String token) {
        SecretKey secretKey = getSecretKey();
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

}
