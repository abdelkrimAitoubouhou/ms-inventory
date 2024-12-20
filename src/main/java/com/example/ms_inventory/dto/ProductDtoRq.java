package com.example.ms_inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoRq {

    @NotEmpty(message = "Model should not be null!")
    private String model;

    @NotNull(message = "Price should not be null!")
    private Double price;

    @NotNull(message = "Quantity should not be null!")
    private Integer qte;

    private String status;
    private Long orderId;


}
