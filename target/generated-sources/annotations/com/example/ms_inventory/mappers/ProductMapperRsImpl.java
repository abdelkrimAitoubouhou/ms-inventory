package com.example.ms_inventory.mappers;

import com.example.ms_inventory.dto.ProductDtoRs;
import com.example.ms_inventory.entities.Product;
import com.example.ms_inventory.entities.Product.ProductBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-11T00:01:36+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class ProductMapperRsImpl implements ProductMapperRs {

    @Override
    public Product toEntity(ProductDtoRs productDtoRs) {
        if ( productDtoRs == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.id( productDtoRs.getId() );
        product.model( productDtoRs.getModel() );
        product.price( productDtoRs.getPrice() );
        product.qte( productDtoRs.getQte() );
        product.status( productDtoRs.getStatus() );
        product.orderId( productDtoRs.getOrderId() );

        return product.build();
    }

    @Override
    public ProductDtoRs toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoRs productDtoRs = new ProductDtoRs();

        if ( product.getStatus() != null ) {
            productDtoRs.setStatus( product.getStatus() );
        }
        else {
            productDtoRs.setStatus( "enable" );
        }
        productDtoRs.setId( product.getId() );
        productDtoRs.setModel( product.getModel() );
        productDtoRs.setPrice( product.getPrice() );
        productDtoRs.setQte( product.getQte() );
        productDtoRs.setOrderId( product.getOrderId() );

        return productDtoRs;
    }

    @Override
    public Product partialUpdate(ProductDtoRs productDtoRs, Product product) {
        if ( productDtoRs == null ) {
            return null;
        }

        if ( productDtoRs.getId() != null ) {
            product.setId( productDtoRs.getId() );
        }
        if ( productDtoRs.getModel() != null ) {
            product.setModel( productDtoRs.getModel() );
        }
        if ( productDtoRs.getPrice() != null ) {
            product.setPrice( productDtoRs.getPrice() );
        }
        if ( productDtoRs.getQte() != null ) {
            product.setQte( productDtoRs.getQte() );
        }
        if ( productDtoRs.getStatus() != null ) {
            product.setStatus( productDtoRs.getStatus() );
        }
        if ( productDtoRs.getOrderId() != null ) {
            product.setOrderId( productDtoRs.getOrderId() );
        }

        return product;
    }
}
