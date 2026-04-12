package co.spring.rest.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.spring.rest.config.JwtConfig;
import co.spring.rest.entity.dto.JwtDto;
import co.spring.rest.entity.dto.PermissionDto;
import co.spring.rest.entity.dto.RoleDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.iservice.IJwtServ;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServ implements IJwtServ{

    @Autowired
    private RoleServ roleServ;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public JwtDto generateAccessToken(UserDto userDto) {
        
        String token = generateToken(userDto, jwtConfig.getExpirationAccessTokenMinute());

        return new JwtDto(userDto.getEmail(), token);
    }

    @Override
    public String generateRefreshToken(UserDto userDto) {
        return generateToken(userDto, jwtConfig.getExpirationRefreshTokenMinute());
    }


    @Override
    public String generateToken(UserDto userDto,long expirationToken) {

        Map<String, Object> claims = new HashMap<>();

        RoleDto roleDto = roleServ.findByName(userDto.getRoleDto().getName());

        claims.put("name", userDto.getName());
        claims.put("role", roleDto.getName());
        claims.put("permission", roleDto.getPermissionDtos());

        Date currentTime = new Date(System.currentTimeMillis());
        
        String token = Jwts.builder()
            .subject(userDto.getEmail())
            .claims(claims)
            .issuedAt(currentTime)
            .expiration(new Date((expirationToken*60*1000)+currentTime.getTime()))
            .signWith(generateKeySecret())
            .compact();

        return token;

    }

    @Override
    public Key generateKeySecret(){
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
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
