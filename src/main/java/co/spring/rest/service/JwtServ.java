package co.spring.rest.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.spring.rest.entity.dto.JwtDto;
import co.spring.rest.iservice.IJwtServ;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServ implements IJwtServ{

    public static String KEY = "secret-key-secret-key-secret-key";

    public static long EXPIRATION_TOKEN_MINUTE = 1;

    @Override
    public JwtDto generateToken(String email) {

        Date currentTime = new Date(System.currentTimeMillis());
        
        String token = Jwts.builder()
            .subject(email)
            .issuedAt(currentTime)
            .expiration(new Date((EXPIRATION_TOKEN_MINUTE*60*1000)+currentTime.getTime()))
            .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
            .compact();

        return new JwtDto(email, token);

    }

}
