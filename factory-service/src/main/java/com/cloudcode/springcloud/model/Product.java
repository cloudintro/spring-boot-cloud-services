package com.cloudcode.springcloud.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {
    @Size(min = 3, max = 15, message = "name must be between 3 to 15 characters")
    private String productName;
    @Min(value = 0, message = "price can not be less than 0")
    private Double productPrice;
}
