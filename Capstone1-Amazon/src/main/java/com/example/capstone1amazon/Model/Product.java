package com.example.capstone1amazon.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "not valid id")
    private String id;
    @NotEmpty(message = "not valid name")
    @Size(min = 4,message = "name length must be more then 3 character")
    private String name;
    @NotNull(message = "not valid price")
    @Positive(message = "price must be positive")
    private int price;
    @NotEmpty(message = "not valid category ID")
    private String categoryID;
    //@Min(value = 1,message = "minimum review 1")
    //@Max(value = 5,message = "maximum review 5")
    //private double review;
}
