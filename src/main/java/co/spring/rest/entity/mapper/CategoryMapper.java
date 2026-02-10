package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.spring.rest.entity.bo.Category;
import co.spring.rest.entity.dto.CategoryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    @InheritInverseConfiguration
    Category toCategory(CategoryDto categoryDto);

}
