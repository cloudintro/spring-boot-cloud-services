package com.cloudcode.springcloud.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
public class ProductRequest {
    private Long id;
    @Size(min = 3, max = 15, message = "name must be between 3 to 15 characters")
    private String name;
    @Min(value = 0, message = "price can not be less than 0")
    private Double price;
}
