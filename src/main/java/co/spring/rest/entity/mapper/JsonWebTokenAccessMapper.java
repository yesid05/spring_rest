package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.JsonWebTokenAccess;
import co.spring.rest.entity.dto.JsonWebTokenAccessDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface JsonWebTokenAccessMapper {

    @Mappings({
        @Mapping(source = "user", target = "userDto")
    })
    JsonWebTokenAccessDto toJsonWebTokenAccessDto(JsonWebTokenAccess jsonWebTokenAccess);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    JsonWebTokenAccess toJsonWebTokenAccess(JsonWebTokenAccessDto jsonWebTokenAccessDto);


}
