package co.spring.rest.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.spring.rest.config.JwtConfig;
import co.spring.rest.entity.bo.JsonWebTokenAccess;
import co.spring.rest.entity.bo.JsonWebTokenRefresh;
import co.spring.rest.entity.dto.JsonWebTokenAccessDto;
import co.spring.rest.entity.dto.JsonWebTokenRefreshDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.JsonWebTokenAccessMapper;
import co.spring.rest.entity.mapper.JsonWebTokenRefreshMapper;
import co.spring.rest.entity.repository.IJsonWebTokenAccessRepository;
import co.spring.rest.entity.repository.IJsonWebTokenRefreshRepository;
import co.spring.rest.iservice.IJwtServ;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServ implements IJwtServ{

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JsonWebTokenAccessMapper jsonWebTokenAccessMapper;

    @Autowired
    private IJsonWebTokenAccessRepository iJsonWebTokenAccessRepository;

    @Autowired
    private JsonWebTokenRefreshMapper jsonWebTokenRefreshMapper;

    @Autowired
    private IJsonWebTokenRefreshRepository iJsonWebTokenRefreshRepository;

    @Override
    public JsonWebTokenAccessDto generateAccessToken(UserDto userDto) {
        
        String token = generateToken(userDto, jwtConfig.getExpirationAccessTokenMinute());

        JsonWebTokenAccessDto jsonWebTokenAccessDto = new JsonWebTokenAccessDto();
        jsonWebTokenAccessDto.setToken(token);
        jsonWebTokenAccessDto.setUserDto(userDto);
        jsonWebTokenAccessDto.setActive(true);

        JsonWebTokenAccess jsonWebTokenAccess = iJsonWebTokenAccessRepository.save(jsonWebTokenAccessMapper.toJsonWebTokenAccess(jsonWebTokenAccessDto));

        return jsonWebTokenAccessMapper.toJsonWebTokenAccessDto(jsonWebTokenAccess);
    }

    @Override
    public String generateRefreshToken(UserDto userDto) {

        String token = generateToken(userDto, jwtConfig.getExpirationRefreshTokenMinute());

        JsonWebTokenRefreshDto jsonWebTokenRefreshDto = new JsonWebTokenRefreshDto();
        jsonWebTokenRefreshDto.setToken(token);
        jsonWebTokenRefreshDto.setUserDto(userDto);
        jsonWebTokenRefreshDto.setActive(true);

        JsonWebTokenRefresh jsonWebTokenRefresh = iJsonWebTokenRefreshRepository.save(jsonWebTokenRefreshMapper.toJsonWebTokenRefresh(jsonWebTokenRefreshDto));

        return jsonWebTokenRefreshMapper.toJsonWebTokenRefreshDto(jsonWebTokenRefresh).getToken();
    }


    @Override
    public String generateToken(UserDto userDto,long expirationToken) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("name", userDto.getName());

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
