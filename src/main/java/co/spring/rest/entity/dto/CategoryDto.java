package co.spring.rest.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private long id;

    @JsonInclude(value = Include.NON_NULL)
    private String name;

    @JsonInclude(value = Include.NON_NULL)
    private String description;

}
