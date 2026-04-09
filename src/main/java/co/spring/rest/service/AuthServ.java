package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.Role;
import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.RoleDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.RoleMapper;
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

    @Autowired
    private RoleServ roleServ;

    @Autowired
    private RoleMapper roleMapper;

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
        
        String nameRole = null;

        try {
            nameRole = userDto.getRoleDto().getName();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        if(nameRole == null )
            nameRole = "CUSTOMER";
            
        RoleDto roleDto = roleServ.findByName(nameRole);

        aUser.setRole(roleMapper.toRole(roleDto));

        return userServ.add(userMapper.toUserDto(aUser));
    }

}
