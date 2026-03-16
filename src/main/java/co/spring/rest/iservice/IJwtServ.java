package co.spring.rest.iservice;

import java.security.Key;

import co.spring.rest.entity.dto.JwtDto;

public interface IJwtServ {

    JwtDto generateToken(String email);

    Key generateKeySecret();

    boolean validateToken(String token);

}
