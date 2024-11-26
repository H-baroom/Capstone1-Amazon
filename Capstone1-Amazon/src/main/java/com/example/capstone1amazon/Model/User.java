package com.example.capstone1amazon.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "not valid id")
    private String id;
    @NotEmpty(message = "not valid userName")
    @Size(min = 6,message = "userName length must be more then 5 character")
    private String userName;
    @NotEmpty(message ="password must not be empty")
    @Size(min = 7, message = "Password must be at least 7 characters long.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*$",
            message = "Password must contain both letters and digits."
    )
    private String password;
    @NotEmpty(message = "email must not be empty")
    @Email(message = "not correct syntax of email")
    private String email;
    @NotEmpty(message = "role must not be empty")
    @Pattern(regexp = "^(Admin|Customer)$",message = "must be Admin or Customer")
    private String role;
    @NotNull(message = "balance must not be empty")
    @Positive(message = "balance must be positive")
    private int balance;
    private ArrayList<Product> buyedProducts = new ArrayList<>();
    private LocalDate registrationDate;
    private int points;
    public void addBuyedProduct(Product product) {
        buyedProducts.add(product);
    }
}
