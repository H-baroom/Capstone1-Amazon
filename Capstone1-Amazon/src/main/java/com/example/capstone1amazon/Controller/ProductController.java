package com.example.capstone1amazon.Controller;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.Product;
import com.example.capstone1amazon.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.addProduct(product)){
            return ResponseEntity.status(200).body(new ApiResponse("Product added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product category not found"));
    }
    @GetMapping("/get")
    public ArrayList<Product> getProduct(){
        return productService.getProduct();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.updateProduct(id, product)) {
            return ResponseEntity.status(200).body(new ApiResponse("product updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        if (productService.deleteProduct(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }
    @PutMapping("/mergeTowCategory/{categoryId1}/{categoryId2}")
    public ResponseEntity mergeTowCategory(@PathVariable String categoryId1,@PathVariable String categoryId2){
        if (productService.mergeTowCategory(categoryId1,categoryId2) == 1){
            return ResponseEntity.status(200).body(new ApiResponse("All products from the second category have been moved to the first category, and the second category has been deleted"));
        } else if (productService.mergeTowCategory(categoryId1,categoryId2) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("First category not exit"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Second Category not exit"));
    }
}
