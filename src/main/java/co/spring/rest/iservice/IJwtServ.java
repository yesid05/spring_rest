package co.spring.rest.iservice;

import co.spring.rest.entity.dto.JwtDto;

public interface IJwtServ {

    JwtDto generateToken(String email);

}
