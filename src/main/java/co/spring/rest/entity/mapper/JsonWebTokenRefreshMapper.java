package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.JsonWebTokenRefresh;
import co.spring.rest.entity.dto.JsonWebTokenRefreshDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface JsonWebTokenRefreshMapper {

    @Mappings({
        @Mapping(source = "user", target = "userDto")
    })
    JsonWebTokenRefreshDto toJsonWebTokenRefreshDto(JsonWebTokenRefresh jsonWebTokenRefresh);

    @InheritInverseConfiguration
    JsonWebTokenRefresh toJsonWebTokenRefresh(JsonWebTokenRefreshDto jsonWebTokenRefreshDto);

}
