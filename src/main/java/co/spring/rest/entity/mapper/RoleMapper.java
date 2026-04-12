package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.Role;
import co.spring.rest.entity.dto.RoleDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    @Mappings(
        @Mapping(source = "permissions", target = "permissionDtos")
    )
    RoleDto toRoleDto(Role role);

    @InheritInverseConfiguration
    Role toRole(RoleDto roleDto);

}
