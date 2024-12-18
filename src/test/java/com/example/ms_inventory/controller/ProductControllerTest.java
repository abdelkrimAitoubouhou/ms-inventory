package com.example.ms_inventory.controller;

import com.example.ms_inventory.dto.ProductDtoRq;
import com.example.ms_inventory.dto.ProductDtoRs;
import com.example.ms_inventory.entities.ApiResponse;
import com.example.ms_inventory.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.class)
@DisplayName("Controller Test")
@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    /* Le test vérifie que :
       °Le contrôleur reçoit correctement une requête HTTP.
       °Le contrôleur traite la requête et appelle le service.
       °Le contrôleur renvoie la réponse attendue.
     */

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;


    @Order(1)
    @DisplayName("add_product_successfully")
    @Test
    void addProduct() throws Exception {

        //Arrange
        ProductDtoRq dtoRq = new ProductDtoRq();
        dtoRq.setQte(new BigDecimal(10));
        dtoRq.setPrice(700);
        dtoRq.setModel("Model1");
        dtoRq.setStatus("enable");

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setId(1L);
        apiResponse.setMessage("Product has been saved successfully");

        Mockito.when(productService.addProduct(any(ProductDtoRq.class))).thenReturn(apiResponse);

        //Act & Assert
        mockMvc.perform(post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRq)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1)) // Verifier que "id" est 1
                .andExpect(jsonPath("$.message").value("Product has been saved successfully")); // Verifier le message

    }

    @Order(2)
    @DisplayName("update_product_successfully")
    @Test
    void updateProduct() throws Exception {

        //Arrange
        Long productId = 1L;

        ProductDtoRq updatedProduct = new ProductDtoRq();
        updatedProduct.setQte(new BigDecimal(3));
        updatedProduct.setPrice(200);
        updatedProduct.setModel("NewModel1");
        updatedProduct.setStatus("enable");

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setId(1L);
        apiResponse.setMessage("Product has been updated successfully");

        Mockito.when(productService.updateProduct(productId, updatedProduct)).thenReturn(apiResponse);

        //Assert and Act
        mockMvc.perform(put("/product/update/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1)) // Verifier que "id" est 1
                .andExpect(jsonPath("$.message").value("Product has been updated successfully")); // Verifier le message


    }

    @Order(3)
    @DisplayName("disable_product_successfully")
    @Test
    void disableProduct() throws Exception {

        //Arrange
        Long productId = 1L;

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setId(1L);
        apiResponse.setMessage("Product has been disabled successfully");

        Mockito.when(productService.disableProduct(productId)).thenReturn(apiResponse);

        //Assert and Act
        mockMvc.perform(get("/product/delete")
                        .param("id", String.valueOf(productId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.message").value("Product has been disabled successfully"));


    }

    @Order(4)
    @DisplayName("get_product_by_id")
    @Test
    void getProduct() throws Exception {

        //Arrange
        Long productId = 1L;

        ProductDtoRs productDtoRs = new ProductDtoRs();
        productDtoRs.setId(productId);
        productDtoRs.setModel("Model1");
        productDtoRs.setPrice(700);
        productDtoRs.setQte(new BigDecimal(10));
        productDtoRs.setStatus("enable");

        // Mock service response
        Mockito.when(productService.getProduct(productId)).thenReturn(productDtoRs);

        // Act and Assert
        mockMvc.perform(get("/product/get")
                        .param("id", String.valueOf(productId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.model").value("Model1"))
                .andExpect(jsonPath("$.price").value(700))
                .andExpect(jsonPath("$.qte").value(10))
                .andExpect(jsonPath("$.status").value("enable"));
    }

    @Order(5)
    @DisplayName("get_all_products")
    @Test
    void getAllProduct() throws Exception {
        ProductDtoRs product1 = new ProductDtoRs();
        product1.setId(1L);
        product1.setModel("Model1");
        product1.setPrice(700);
        product1.setQte(new BigDecimal(10));
        product1.setStatus("enable");

        ProductDtoRs product2 = new ProductDtoRs();
        product2.setId(2L);
        product2.setModel("Model2");
        product2.setPrice(500);
        product2.setQte(new BigDecimal(5));
        product2.setStatus("disable");

        List<ProductDtoRs> products = Arrays.asList(product1, product2);

        // Mock service response
        Mockito.when(productService.getAllProduct()).thenReturn(products);

        // Act and Assert
        mockMvc.perform(get("/product/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].model").value("Model1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].model").value("Model2"));
    }


}