package co.spring.rest.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.JwtDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.iservice.IJwtServ;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServ implements IJwtServ{

    @Value("${spring.jwt.secret}")
    private String KEY;

    @Value("${spring.jwt.expiration}")
    private long EXPIRATION_TOKEN_MINUTE;

    @Override
    public JwtDto generateToken(UserDto userDto) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("name", userDto.getName());

        Date currentTime = new Date(System.currentTimeMillis());
        
        String token = Jwts.builder()
            .subject(userDto.getEmail())
            .claims(claims)
            .issuedAt(currentTime)
            .expiration(new Date((EXPIRATION_TOKEN_MINUTE*60*1000)+currentTime.getTime()))
            .signWith(generateKeySecret())
            .compact();

        return new JwtDto(userDto.getEmail(), token);

    }

    @Override
    public Key generateKeySecret(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

    @Override
    public boolean validateToken(String token) {

        try {
            
            Claims claims = getClaims(token);

            return claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            return false;
        }

    }

    @Override
    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey)generateKeySecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String getTokenRequest(HttpServletRequest httpServletRequest) {
        
        String aAuthorization = httpServletRequest.getHeader("Authorization");

        if(!StringUtils.hasText(aAuthorization) || !aAuthorization.startsWith("Bearer ")) {
			return null;
		}

        return aAuthorization.replace("Bearer ", "");


    }

}
