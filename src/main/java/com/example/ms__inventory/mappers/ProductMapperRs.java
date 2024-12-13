package com.example.ms__inventory.mappers;

import com.example.ms__inventory.dto.ProductDtoRs;
import com.example.ms__inventory.entities.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapperRs {
    Product toEntity(ProductDtoRs productDtoRs);

    @Mapping(target = "status", defaultValue = "enable")
    ProductDtoRs toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDtoRs productDtoRs, @MappingTarget Product product);
}