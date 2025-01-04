package com.example.ms_inventory.controller;

import com.example.ms_inventory.dto.ProductDtoRq;
import com.example.ms_inventory.dto.ProductDtoRs;
import com.example.ms_inventory.entities.ApiResponse;
import com.example.ms_inventory.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse addProduct(@RequestBody @Valid ProductDtoRq dtoRq) {
      return  productService.addProduct(dtoRq);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse updateProduct(@PathVariable Long id,
                                     @RequestBody ProductDtoRq dtoRq){
       return productService.updateProduct(id, dtoRq);
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse disableProduct(@RequestParam Long id){
        return productService.disableProduct(id);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('user')")
    public ProductDtoRs getProduct(@RequestParam Long id){
        return productService.getProduct(id);
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('user')")
    public List<ProductDtoRs> getAllProduct(    ) {
        return productService.getAllProduct();
    }

    @GetMapping("/order/{order_id}")
    @PreAuthorize("hasAuthority('admin')")
    public List<ProductDtoRs> getAllProductsByOrderId(@PathVariable("admin") Long orderId) {
        return productService.getAllProductsByOrderId(orderId);
    }


    @GetMapping("/check-product/{product_id}/{product_qte}")
    public boolean checkProduct(@PathVariable("product_id") Long id,
                                    @PathVariable("product_qte") @NotNull  Integer qte) {
        return productService.checkProduct(id, qte);
    }


    @GetMapping("/availability/{product_id}")
    boolean enableOrDisableProduct(@PathVariable("product_id") Long id) {
       return productService.enableOrDisableProduct(id);
    }


}

