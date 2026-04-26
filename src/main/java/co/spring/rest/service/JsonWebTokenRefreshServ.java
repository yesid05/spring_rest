package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.JsonWebTokenRefresh;
import co.spring.rest.entity.dto.JsonWebTokenRefreshDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.JsonWebTokenRefreshMapper;
import co.spring.rest.entity.repository.IJsonWebTokenRefreshRepository;
import co.spring.rest.iservice.IJsonWebTokenRefreshServ;

@Service
public class JsonWebTokenRefreshServ extends JwtServ implements IJsonWebTokenRefreshServ {

    @Autowired
    private JsonWebTokenRefreshMapper jsonWebTokenRefreshMapper;

    @Autowired
    private IJsonWebTokenRefreshRepository iJsonWebTokenRefreshRepository;

    @Override
    public String generateRefreshToken(UserDto userDto){
        String token = generateToken(userDto, jwtConfig.getExpirationRefreshTokenMinute());

        JsonWebTokenRefreshDto jsonWebTokenRefreshDto = new JsonWebTokenRefreshDto();
        jsonWebTokenRefreshDto.setToken(token);
        jsonWebTokenRefreshDto.setUserDto(userDto);
        jsonWebTokenRefreshDto.setActive(true);

        JsonWebTokenRefresh jsonWebTokenRefresh = iJsonWebTokenRefreshRepository.save(jsonWebTokenRefreshMapper.toJsonWebTokenRefresh(jsonWebTokenRefreshDto));

        return jsonWebTokenRefreshMapper.toJsonWebTokenRefreshDto(jsonWebTokenRefresh).getToken();
    }

    @Override
    public boolean disableToken(String token) {
        
        boolean disableToken = false;

        JsonWebTokenRefresh jsonWebTokenRefresh = iJsonWebTokenRefreshRepository.findByToken(token).orElse(null);

        if(jsonWebTokenRefresh != null){

            jsonWebTokenRefresh.setActive(disableToken);
            iJsonWebTokenRefreshRepository.save(jsonWebTokenRefresh);

            disableToken = true;

        }


        return disableToken;

    }

    @Override
    public boolean isActiveToken(String token) {
        
        return iJsonWebTokenRefreshRepository.findByToken(token)
            .map(aToken -> aToken.isActive()).orElse(false);

    }

}
