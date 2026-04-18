package co.spring.rest.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    @JsonIgnore
    private long id;

    private String name;

    @JsonInclude(value = Include.NON_NULL)
    @JsonProperty("permissions")
    private List<PermissionDto> permissionDtos;

}
