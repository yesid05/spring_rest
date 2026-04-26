package co.spring.rest.iservice;

import java.security.Key;

import co.spring.rest.entity.dto.UserDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtServ {

    String generateToken(UserDto userDto,long expirationToken);

    Claims getClaims(String token);

    String getTokenRequest(HttpServletRequest httpServletRequest);

    Key generateKeySecret();

    boolean validateToken(String token);

    boolean disableToken(String token);

    boolean isActiveToken(String token);

}
