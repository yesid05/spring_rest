package co.spring.rest.iservice;

import java.security.Key;

import co.spring.rest.entity.dto.JwtDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtServ {

    JwtDto generateToken(String email);

    Claims getClaims(String token);

    String getTokenRequest(HttpServletRequest httpServletRequest);

    Key generateKeySecret();

    boolean validateToken(String token);

}
