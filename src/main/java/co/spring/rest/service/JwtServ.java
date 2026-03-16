package co.spring.rest.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.dto.JwtDto;
import co.spring.rest.iservice.IJwtServ;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServ implements IJwtServ{

    @Value("${spring.jwt.secret}")
    private String KEY;

    @Value("${spring.jwt.expiration}")
    private long EXPIRATION_TOKEN_MINUTE;

    @Override
    public JwtDto generateToken(String email) {

        Date currentTime = new Date(System.currentTimeMillis());
        
        String token = Jwts.builder()
            .subject(email)
            .issuedAt(currentTime)
            .expiration(new Date((EXPIRATION_TOKEN_MINUTE*60*1000)+currentTime.getTime()))
            .signWith(generateKeySecret())
            .compact();

        return new JwtDto(email, token);

    }

    @Override
    public Key generateKeySecret(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

    @Override
    public boolean validateToken(String token) {

        try {
            
            Claims claims = Jwts
                .parser()
                .verifyWith((SecretKey)generateKeySecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();

                return claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            return false;
        }

    }

}
