package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.spring.rest.entity.bo.Role;
import co.spring.rest.entity.dto.RoleDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleDto toRoleDto(Role role);

    @InheritInverseConfiguration
    Role toRole(RoleDto roleDto);

}
