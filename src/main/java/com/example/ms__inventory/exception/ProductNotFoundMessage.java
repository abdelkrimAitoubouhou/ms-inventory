package com.example.ms__inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductNotFoundMessage {


    public static String message = "No product founded with id : = ";
}
