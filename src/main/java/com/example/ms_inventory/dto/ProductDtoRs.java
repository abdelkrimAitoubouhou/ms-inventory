package com.example.ms_inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDtoRs {

    private Long id;
    @NotEmpty(message = "Model should not be null!")
    private String model;

    @NotNull(message = "Price should not be null!")
    private Double price;

    @NotNull(message = "Quantity should not be null!")
    private Integer qte;

    @NotEmpty(message = "Status should not be null!")
    private String status;
    private Long orderId;


}
