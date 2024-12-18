package com.example.ms_inventory.exception;

import lombok.Data;

@Data
public class ProductNotFoundMessage {

    public static final String MESSAGE = "No product founded with id : = ";

    private ProductNotFoundMessage() {
        throw new IllegalStateException("This class return an exception when a product not found");
    }
}
