package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mappings({
        @Mapping(source = "birthDay", target = "birthDay", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "salary", target = "salary", numberFormat = "$#.00")
    })
    UserDto toUserDto(User user);

    @InheritInverseConfiguration
    User toUser(UserDto userDto);

}
