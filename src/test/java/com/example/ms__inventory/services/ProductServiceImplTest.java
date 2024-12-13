package com.example.ms__inventory.services;

import com.example.ms__inventory.dto.ProductDtoRq;
import com.example.ms__inventory.dto.ProductDtoRs;
import com.example.ms__inventory.entities.ApiResponse;
import com.example.ms__inventory.entities.Product;
import com.example.ms__inventory.exception.ProductNotFoundException;
import com.example.ms__inventory.mappers.ProductMapperRq;
import com.example.ms__inventory.mappers.ProductMapperRs;
import com.example.ms__inventory.repositories.ProductRepository;
import com.example.ms__inventory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@TestMethodOrder(MethodOrderer.class)
@DisplayName("Product Test")
@ExtendWith(MockitoExtension.class) //Mockito will generate all the annotations
class ProductServiceImplTest {

    @Mock //Mock the productRepository dependency
    private ProductRepository productRepository;

    @Mock //Mock the productMapperRq dependency
    private ProductMapperRq productMapperRq;

    @Mock //Mock the productMapperRq dependency
    private ProductMapperRs productMapperRs;


    @InjectMocks //Call the class that we want to test and inject all the dependencies with @Mock
    private ProductServiceImpl productService;


    @Test
    @Order(1)
    @DisplayName("add product")
    void AddProduct_ShouldSaveProductSuccessfully() {

        // Arrange : Prepare all the objects and variables we need to execute the test
        Product product = new Product();
        product.setId(1L); // add an ID
        product.setPrice(1000);
        product.setQte(new BigDecimal(10));
        product.setModel("Model1");

        ProductDtoRq productDtoRq = new ProductDtoRq();
        productDtoRq.setPrice(1000);
        productDtoRq.setQte(new BigDecimal(10));
        productDtoRq.setModel("Model1");

        when(productMapperRq.toEntity(productDtoRq)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);


        // Act : call the method we want to test
        ApiResponse response = productService.addProduct(productDtoRq);

        // Assert : assert the results
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Product has been saved successfully", response.getMessage());

        // Make sure that mocks are called just one time
        verify(productMapperRq, times(1)).toEntity(any(ProductDtoRq.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    @Order(2)
    @DisplayName("updateProduct - Should Update Product Successfully")
    void UpdateProduct_ShouldSaveProductSuccessfully() {

        // Arrange : Prepare all the objects and variables we need to execute the test
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setModel("OldModel");
        existingProduct.setPrice(100);
        existingProduct.setQte(new BigDecimal(5));

        ProductDtoRq updatedProductDto;
        updatedProductDto = new ProductDtoRq();
        updatedProductDto.setModel("NewModel");
        updatedProductDto.setPrice(200);
        updatedProductDto.setQte(new BigDecimal(10));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct)); //Mock the productRepository.findById() method
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct); //Mock the productRepository.save() method

        // Act : call the method we want to test
        ApiResponse response = productService.updateProduct(productId, updatedProductDto);

        // Assert : assert the results
        assertNotNull(response);
        assertEquals(productId, response.getId());
        assertEquals("Product has been updated successfully!", response.getMessage());

        // Make sure that the attributes have been updated
        assertEquals("NewModel", existingProduct.getModel());
        assertEquals(200, existingProduct.getPrice());
        assertEquals(new BigDecimal(10), existingProduct.getQte());

        // Assert the interactions with the MOCK
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }


    @Test
    @Order(3)
    @DisplayName("updateProduct - Should Throw Exception If Product Not Found")
    void UpdateProduct_ShouldThrowExceptionIfProductNotFound() {

        // Arrange : Prepare all the objects and variables we need to execute the test
        Long productId = 1L;
        ProductDtoRq updatedProductDto = new ProductDtoRq();
        updatedProductDto.setModel("NewModel");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act : call the method we want to test
        RuntimeException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(productId, updatedProductDto);
        });

        assertEquals("No product founded with id : = 1", exception.getMessage());

        // Assert the interactions with the MOCK
        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }






    @Test
    @Order(4)
    @DisplayName("disableProduct - Should Disable Product Successfully")
    void disableProduct_ShouldDisableProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStatus("active");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ApiResponse response = productService.disableProduct(productId);

        // Assert
        assertNotNull(response);
        assertEquals(productId, response.getId());
        assertEquals("Product has been disabled successfully!", response.getMessage());
        assertEquals("disable", product.getStatus());

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }

    @Test
    @Order(5)
    @DisplayName("disableProduct - Should Throw Exception If Product Not Found")
    void disableProduct_ShouldThrowExceptionIfProductNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.disableProduct(productId);
        });

        assertEquals("No product founded with id : = 1", exception.getMessage());

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }



    @Test
    @Order(8)
    @DisplayName("getProduct - Should Return Product Successfully")
    void getProduct_ShouldReturnProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setModel("Product1");

        ProductDtoRs productDtoRs = new ProductDtoRs();
        productDtoRs.setId(productId);
        productDtoRs.setModel("Product1");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapperRs.toDto(product)).thenReturn(productDtoRs);

        // Act
        ProductDtoRs response = productService.getProduct(productId);

        // Assert
        assertNotNull(response);
        assertEquals(productId, response.getId());
        assertEquals("Product1", response.getModel());

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productMapperRs).toDto(product);
    }


    @Test
    @Order(7)
    @DisplayName("getProduct - Should Throw Exception If Product Not Found")
    void getProduct_ShouldThrowExceptionIfProductNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct(productId);
        });

        assertEquals("No product founded with id : = 1", exception.getMessage());

        // Verify interactions
        verify(productRepository).findById(productId);
        verify(productMapperRs, never()).toDto(any(Product.class));
    }


    @Test
    @Order(6)
    @DisplayName("getAllProduct - Should Return All Products Successfully")
    void getAllProduct_ShouldReturnAllProductsSuccessfully() {

        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setModel("Product1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setModel("Product2");

        ProductDtoRs dto1 = new ProductDtoRs();
        dto1.setId(1L);
        dto1.setModel("Product1");

        ProductDtoRs dto2 = new ProductDtoRs();
        dto2.setId(2L);
        dto2.setModel("Product2");

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapperRs.toDto(product1)).thenReturn(dto1);
        when(productMapperRs.toDto(product2)).thenReturn(dto2);

        // Act
        List<ProductDtoRs> response = productService.getAllProduct();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Product1", response.get(0).getModel());
        assertEquals("Product2", response.get(1).getModel());

        // Verify interactions
        verify(productRepository).findAll();
        verify(productMapperRs).toDto(product1);
        verify(productMapperRs).toDto(product2);
    }






}