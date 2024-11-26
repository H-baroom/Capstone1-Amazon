package com.example.capstone1amazon.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "not valid id")
    private String id;
    @NotEmpty(message = "not valid product ID")
    private String productID;
    @NotEmpty(message = "not valid marchant ID")
    private String marchantID;
    @Positive(message = "not valid stock")
    @Min(value = 10,message = "stock must be start by 10")
    private int stock;
}
