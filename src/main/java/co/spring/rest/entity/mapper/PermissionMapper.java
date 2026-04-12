package co.spring.rest.entity.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.spring.rest.entity.bo.Permission;
import co.spring.rest.entity.dto.PermissionDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {

    PermissionDto toPermissionDto(Permission permission);

    @InheritInverseConfiguration
    Permission toPermission(PermissionDto permissionDto);

    List<PermissionDto> toPermissionDtoList(List<Permission> permissions);

    List<Permission> toPermissionList(List<PermissionDto> permissionDtos);

}
