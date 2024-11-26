package com.example.capstone1amazon.Controller;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.Category;
import com.example.capstone1amazon.Service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final View error;

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategories(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added"));
    }
    @GetMapping("/get")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id,@RequestBody @Valid Category category,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (categoryService.updateCategory(id,category)){
            return ResponseEntity.status(200).body(new ApiResponse("Category updated"));
        }
            return ResponseEntity.status(400).body(new ApiResponse("Category not exit"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){
        if (categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not exit"));
    }

    /*
    @PutMapping("/mergeTowCategory/{categoryId1}/{categoryId2}")
    public ResponseEntity mergeTowCategory(@PathVariable String categoryId1,@PathVariable String categoryId2){
        if (categoryService.mergeTowCategory(categoryId1,categoryId2) == 1){
            return ResponseEntity.status(200).body(new ApiResponse("All products from the second category have been moved to the first category, and the second category has been deleted"));
        } else if (categoryService.mergeTowCategory(categoryId1,categoryId2) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("First category not exit"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Second Category not exit"));
    }
     */
}
