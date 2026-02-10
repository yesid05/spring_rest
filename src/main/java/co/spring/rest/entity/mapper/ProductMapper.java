package co.spring.rest.entity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import co.spring.rest.entity.bo.Product;
import co.spring.rest.entity.dto.ProductDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mappings({
        @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "categoria", target = "categoryDto")
    })
    ProductDto toProductDto(Product product);

    @InheritInverseConfiguration
    Product toProduct(ProductDto productDto);
}
