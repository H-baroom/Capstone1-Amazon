package com.example.capstone1amazon.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "not valid id")
    private String id;
    @NotEmpty(message = "not valid name")
    @Size(min = 4,message = "name length must be more then 3 character")
    private String name;
}
