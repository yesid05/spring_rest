package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper {

    @Mappings({
        @Mapping(source = "birthDay", target = "birthDay", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "salary", target = "salary", numberFormat = "$#.00"),
        @Mapping(source = "role", target = "roleDto")
    })
    UserDto toUserDto(User user);

    @InheritInverseConfiguration
    User toUser(UserDto userDto);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "birthDay", target = "birthDay", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "salary", target = "salary", numberFormat = "$#.00")
    })
    User update(UserDto userDto, @MappingTarget User user);

}
