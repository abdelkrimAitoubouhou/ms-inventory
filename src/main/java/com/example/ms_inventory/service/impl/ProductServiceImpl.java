package com.example.ms_inventory.service.impl;

import com.example.ms_inventory.dto.ProductDtoRq;
import com.example.ms_inventory.dto.ProductDtoRs;
import com.example.ms_inventory.entities.ApiResponse;
import com.example.ms_inventory.entities.Product;
import com.example.ms_inventory.exception.ProductNotFoundException;
import com.example.ms_inventory.exception.ProductNotFoundMessage;
import com.example.ms_inventory.mappers.ProductMapperRq;
import com.example.ms_inventory.mappers.ProductMapperRs;
import com.example.ms_inventory.repositories.ProductRepository;
import com.example.ms_inventory.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapperRq productMapperRq;
    private final ProductMapperRs productMapperRs;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapperRq productMapperRq,
                              ProductMapperRs productMapperRs) {
        this.productRepository = productRepository;
        this.productMapperRq = productMapperRq;
        this.productMapperRs = productMapperRs;
    }

    @Override
    public ApiResponse addProduct(ProductDtoRq dtoRq) {

        Product product = productMapperRq.toEntity(dtoRq);
        productRepository.save(product);
        return ApiResponse.builder()
                .id(product.getId())
                .message("Product has been saved successfully")
                .build();
    }

    @Override
    public ApiResponse updateProduct(Long id, ProductDtoRq dtoRq) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundMessage.MESSAGE + id));

        if (dtoRq.getModel() != null) {
            product.setModel(dtoRq.getModel());
        }

        if (dtoRq.getPrice() != null) {
            product.setPrice(dtoRq.getPrice());
        }

        if (dtoRq.getQte() != null) {
            product.setQte(dtoRq.getQte());
            if (product.getQte() > 0)
                product.setStatus("available");
            else product.setStatus("not available");
        }

        if (dtoRq.getStatus() != null) {
            product.setStatus(dtoRq.getStatus());
        }
        productRepository.save(product);

        return ApiResponse.builder()
                .id(product.getId())
                .message("Product has been updated successfully!")
                .build();
    }

    @Override
    public ApiResponse disableProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundMessage.MESSAGE + id));

        product.setStatus("not available");
        productRepository.save(product);
        return ApiResponse.builder()
                .id(id)
                .message("Product has been disabled successfully!")
                .build();
    }


    @Override
    public ProductDtoRs getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundMessage.MESSAGE + id));
        return productMapperRs.toDto(product);
    }

    @Override
    public List<ProductDtoRs> getAllProduct() {

        List<Product> products = productRepository.findAllProductsByStatus("available");
        List<ProductDtoRs> dtoRs = new ArrayList<>();
        products.forEach(e -> dtoRs.add(productMapperRs.toDto(e)));
        return dtoRs;
    }

    @Override
    public List<ProductDtoRs> getAllProductsByOrderId(Long orderId) {

        List<ProductDtoRs> dtoRs = new ArrayList<>();
        productRepository.findAllByOrderId(orderId).forEach(product -> {
            ProductDtoRs productDtoRs = productMapperRs.toDto(product);
            dtoRs.add(productDtoRs);
        });

        return dtoRs;
    }

    @Override
    public boolean checkProduct(Long id, Integer qte) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundMessage.MESSAGE + id));

        if (product.getQte() - qte < 0) //product.getQte() and qte are not-equal in value
            return false;

        product.setQte(product.getQte()-qte);
        productRepository.save(product);
        return true;
    }

    @Override
    public boolean enableOrDisableProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundMessage.MESSAGE + id));

        if (product.getQte() == 0)
            product.setStatus("not available");
        productRepository.save(product);
        return true;

    }


}
