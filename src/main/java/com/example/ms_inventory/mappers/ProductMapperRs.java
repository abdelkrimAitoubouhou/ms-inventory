package com.example.ms_inventory.mappers;

import com.example.ms_inventory.dto.ProductDtoRs;
import com.example.ms_inventory.entities.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapperRs {
    Product toEntity(ProductDtoRs productDtoRs);

    @Mapping(target = "status", defaultValue = "enable")
    ProductDtoRs toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDtoRs productDtoRs, @MappingTarget Product product);
}