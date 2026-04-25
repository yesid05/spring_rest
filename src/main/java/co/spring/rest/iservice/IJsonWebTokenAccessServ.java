package co.spring.rest.iservice;

import co.spring.rest.entity.dto.JsonWebTokenAccessDto;
import co.spring.rest.entity.dto.UserDto;

public interface IJsonWebTokenAccessServ {

    JsonWebTokenAccessDto generateAccessToken(UserDto userDto);

}
