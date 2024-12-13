package com.example.ms__inventory.service;

import com.example.ms__inventory.dto.ProductDtoRq;
import com.example.ms__inventory.dto.ProductDtoRs;
import com.example.ms__inventory.entities.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService  {
    ApiResponse addProduct(ProductDtoRq dtoRq);
    ApiResponse updateProduct(Long id, ProductDtoRq dtoRq);
    ApiResponse disableProduct(Long id);
    ProductDtoRs getProduct(Long id);
    List<ProductDtoRs> getAllProduct();
}
