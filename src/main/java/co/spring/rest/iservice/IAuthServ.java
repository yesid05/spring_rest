package co.spring.rest.iservice;

import co.spring.rest.entity.dto.UserDto;

public interface IAuthServ {

    UserDto registerUser(UserDto userDto);

    String login(String email, String password);

}
