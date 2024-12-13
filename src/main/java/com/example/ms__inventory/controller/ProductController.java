package com.example.ms__inventory.controller;

import com.example.ms__inventory.dto.ProductDtoRq;
import com.example.ms__inventory.dto.ProductDtoRs;
import com.example.ms__inventory.entities.ApiResponse;
import com.example.ms__inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @PostMapping("/add")
    public ApiResponse addProduct(@RequestBody @Valid ProductDtoRq dtoRq) {
      return  productService.addProduct(dtoRq);
    }


    @PutMapping("/update/{id}")
    public ApiResponse updateProduct(@PathVariable Long id,
                                     @RequestBody ProductDtoRq dtoRq){
       return productService.updateProduct(id, dtoRq);
    }

    @GetMapping("/delete")
    public ApiResponse disableProduct(@RequestParam Long id){
        return productService.disableProduct(id);
    }

    @GetMapping("/get")
    public ProductDtoRs getProduct(@RequestParam Long id){
        return productService.getProduct(id);
    }


    @GetMapping("/getAll")
    public List<ProductDtoRs> getAllProduct(){
        return productService.getAllProduct();
    }



}

