package co.spring.rest.iservice;

import co.spring.rest.entity.dto.UserDto;

public interface IJsonWebTokenRefreshServ {
    
    String generateRefreshToken(UserDto userDto);

}
