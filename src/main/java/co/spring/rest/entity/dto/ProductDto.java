package co.spring.rest.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private long id;

    private String name;

    private String description;

    @JsonProperty("release_date")
    private String releaseDate;

    private String img;

    @JsonProperty("category")
    private CategoryDto categoryDto;



}
