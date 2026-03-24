package co.spring.rest.iservice;

import co.spring.rest.entity.dto.UserDto;

public interface IAuthServ {

    UserDto registerUser(UserDto userDto);

    UserDto login(String email, String password);

}
