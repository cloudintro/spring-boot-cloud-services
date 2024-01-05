package com.cloudcode.springcloud.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @Size(min = 3, max = 15, message = "name must be between 3 to 15 characters")
    private String name;
    @Min(value = 0, message = "price can not be less than 0")
    private Double price;
}
