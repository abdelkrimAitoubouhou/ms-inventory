package com.example.ms_inventory.mappers;

import com.example.ms_inventory.dto.ProductDtoRq;
import com.example.ms_inventory.entities.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapperRq {
    @Mapping(target = "status", defaultValue = "available")
    Product toEntity(ProductDtoRq productDtoRq);

    ProductDtoRq toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDtoRq productDtoRq, @MappingTarget Product product);


    // Mapping method for updating existing Product
    void updateProductFromDTO(ProductDtoRq dtoRq, @MappingTarget Product product);
}