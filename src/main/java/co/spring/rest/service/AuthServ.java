package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.UserMapper;
import co.spring.rest.error.CreatedError;
import co.spring.rest.iservice.IAuthServ;

@Service
public class AuthServ implements IAuthServ{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServ userServ;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto login(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        return userMapper.toUserDto((User)authentication.getPrincipal());

    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        if(userServ.existsByEmail(userDto.getEmail()))
            throw new CreatedError("User not created", "User not created, email already exists", null);

        User aUser = userMapper.toUser(userDto);
        aUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userServ.add(userMapper.toUserDto(aUser));
    }

}
