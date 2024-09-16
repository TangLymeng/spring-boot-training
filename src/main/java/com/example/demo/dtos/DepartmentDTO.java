package com.example.demo.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotEmpty(message = "Code cannot be empty")
    private String code;

    // You can add any additional fields or methods if needed
}