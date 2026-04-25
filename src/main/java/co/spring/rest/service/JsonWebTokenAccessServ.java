package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.JsonWebTokenAccess;
import co.spring.rest.entity.dto.JsonWebTokenAccessDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.JsonWebTokenAccessMapper;
import co.spring.rest.entity.repository.IJsonWebTokenAccessRepository;
import co.spring.rest.iservice.IJsonWebTokenAccessServ;

@Service
public class JsonWebTokenAccessServ extends JwtServ implements IJsonWebTokenAccessServ {

    @Autowired
    private JsonWebTokenAccessMapper jsonWebTokenAccessMapper;

    @Autowired
    private IJsonWebTokenAccessRepository iJsonWebTokenAccessRepository;

    @Override
    public JsonWebTokenAccessDto generateAccessToken(UserDto userDto){
        String token = super.generateToken(userDto, jwtConfig.getExpirationAccessTokenMinute());

        JsonWebTokenAccessDto jsonWebTokenAccessDto = new JsonWebTokenAccessDto();
        jsonWebTokenAccessDto.setToken(token);
        jsonWebTokenAccessDto.setUserDto(userDto);
        jsonWebTokenAccessDto.setActive(true);

        JsonWebTokenAccess jsonWebTokenAccess = iJsonWebTokenAccessRepository.save(jsonWebTokenAccessMapper.toJsonWebTokenAccess(jsonWebTokenAccessDto));

        return jsonWebTokenAccessMapper.toJsonWebTokenAccessDto(jsonWebTokenAccess);
    }


}
