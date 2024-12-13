package com.example.ms__inventory.repository;

import com.example.ms__inventory.entities.Product;
import com.example.ms__inventory.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.class)
@DisplayName("Product Repository Test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("save product")
    @Order(1)
    void ProductRepository_SaveProduct_ReturnSavedProduct() {

        //Arrange
        Product product = new Product();
        product.setQte(BigDecimal.valueOf(3));
        product.setPrice(3000);
        product.setModel("Model1");
        product.setStatus("enable");


        //Act
        Product savedProduct = productRepository.save(product);

        //Assert
        assertNotNull(savedProduct.getId(), "The product ID should be generated");
        assertEquals("Model1", savedProduct.getModel());
        assertEquals(3000, savedProduct.getPrice());
        assertEquals(BigDecimal.valueOf(3), savedProduct.getQte());
        assertEquals("enable", savedProduct.getStatus());
    }


    @Test
    @DisplayName("find product by ID")
    @Order(2)
    void ProductRepository_FindProductById_ReturnProductById() {

        //Arrange
        Product product = new Product();
        product.setQte(BigDecimal.valueOf(3));
        product.setPrice(3000);
        product.setModel("Model22");
        product.setStatus("enable");
        Product savedProduct = productRepository.save(product);

        //Act
        Product productColl = productRepository.findById(savedProduct.getId()).get();

        //Assert
        assertNotNull(productColl, "The product should exist in the database");
        assertEquals("Model22", productColl.getModel());


    }

    @Test
    void findAllProducts() {

        //Arrange
        Product product = new Product();
        product.setQte(BigDecimal.valueOf(3));
        product.setPrice(3000);
        product.setModel("Model22");
        product.setStatus("enable");
        Product savedProduct = productRepository.save(product);

        List<Product> products = new ArrayList<>();
        products.add(savedProduct);

        //Act
        List<Product> list = productRepository.findAll();

        //Assert
        assertNotNull(list);
        assertEquals(products.size(), list.size());


    }

}

