package com.example.ms_inventory.mappers;

import com.example.ms_inventory.dto.ProductDtoRq;
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
public class ProductMapperRqImpl implements ProductMapperRq {

    @Override
    public Product toEntity(ProductDtoRq productDtoRq) {
        if ( productDtoRq == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        if ( productDtoRq.getStatus() != null ) {
            product.status( productDtoRq.getStatus() );
        }
        else {
            product.status( "available" );
        }
        product.model( productDtoRq.getModel() );
        product.price( productDtoRq.getPrice() );
        product.qte( productDtoRq.getQte() );
        product.orderId( productDtoRq.getOrderId() );

        return product.build();
    }

    @Override
    public ProductDtoRq toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoRq productDtoRq = new ProductDtoRq();

        productDtoRq.setModel( product.getModel() );
        productDtoRq.setPrice( product.getPrice() );
        productDtoRq.setQte( product.getQte() );
        productDtoRq.setStatus( product.getStatus() );
        productDtoRq.setOrderId( product.getOrderId() );

        return productDtoRq;
    }

    @Override
    public Product partialUpdate(ProductDtoRq productDtoRq, Product product) {
        if ( productDtoRq == null ) {
            return null;
        }

        if ( productDtoRq.getModel() != null ) {
            product.setModel( productDtoRq.getModel() );
        }
        if ( productDtoRq.getPrice() != null ) {
            product.setPrice( productDtoRq.getPrice() );
        }
        if ( productDtoRq.getQte() != null ) {
            product.setQte( productDtoRq.getQte() );
        }
        if ( productDtoRq.getStatus() != null ) {
            product.setStatus( productDtoRq.getStatus() );
        }
        if ( productDtoRq.getOrderId() != null ) {
            product.setOrderId( productDtoRq.getOrderId() );
        }

        return product;
    }

    @Override
    public void updateProductFromDTO(ProductDtoRq dtoRq, Product product) {
        if ( dtoRq == null ) {
            return;
        }

        product.setModel( dtoRq.getModel() );
        product.setPrice( dtoRq.getPrice() );
        product.setQte( dtoRq.getQte() );
        product.setStatus( dtoRq.getStatus() );
        product.setOrderId( dtoRq.getOrderId() );
    }
}
