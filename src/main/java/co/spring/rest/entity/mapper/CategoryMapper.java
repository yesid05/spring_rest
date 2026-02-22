package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.Category;
import co.spring.rest.entity.dto.CategoryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mappings({
        @Mapping(source = "products", target = "productDtos")
    })
    CategoryDto toCategoryDto(Category category);

    @InheritInverseConfiguration
    Category toCategory(CategoryDto categoryDto);

}
