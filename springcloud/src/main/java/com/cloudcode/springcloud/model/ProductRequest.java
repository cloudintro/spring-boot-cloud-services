package com.cloudcode.springcloud.model;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter
public class ProductRequest {
    @NotNull(message = "id must not be empty")
    private long id;
    @Size(min = 3, max = 10, message = "name must be minimum 3 and maximum 10 characters")
    private String name;
    @Min(value = 1, message = "price can not be less than 1")
    private double price;
}
