package com.example.ms__inventory.entities;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Long id;
    private String message;

}
