package co.spring.rest.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 150, message =  "Name must be less than 150  characters")
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 150, message =  "Description must be less than 255  characters")
    private String description;

    @NotNull(message = "Date is required")
    @Pattern(
        regexp = "\\b\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\b",
        message = "The date must have the following format: yyyy-MM-dd"
    )
    @JsonProperty("release_date")
    private String releaseDate;

    @NotNull(message = "Image is required")
    @NotBlank(message = "Image is required")
    private String img;

    @JsonProperty("category")
    @JsonInclude(value = Include.NON_NULL)
    @NotNull(message = "Category is required")
    private CategoryDto categoryDto;



}
