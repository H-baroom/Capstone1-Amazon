package com.example.capstone1amazon.Controller;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.User;
import com.example.capstone1amazon.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }
    @GetMapping("/get")
    public ResponseEntity getUser() {
        return ResponseEntity.status(200).body(userService.getUsers());
    }
    @PutMapping("/update/{id}/{uodateDateToTest}")
    public ResponseEntity updateUser(@PathVariable String id,@PathVariable LocalDate uodateDateToTest ,@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.updateUser(id,uodateDateToTest,user)){
            return ResponseEntity.status(200).body(new ApiResponse("User updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Not Found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User Not Found"));
    }

    @PutMapping("/buyProductDirectly/{userID}/{productID}/{merchantID}")
    public ResponseEntity buyProductDirectly(@PathVariable String userID,@PathVariable String productID,@PathVariable String merchantID){
        if (userService.buyProductDirectly(userID,productID,merchantID) == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("buy product directly successfully"));
        } else if (userService.buyProductDirectly(userID,productID,merchantID) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Marchant ID not correct"));
        } else if (userService.buyProductDirectly(userID,productID,merchantID) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("balance not enough"));
        }else if (userService.buyProductDirectly(userID,productID,merchantID) == 4) {
            return ResponseEntity.status(400).body(new ApiResponse("out of stock"));
        } else if (userService.buyProductDirectly(userID,productID,merchantID) == 5) {
            return ResponseEntity.status(400).body(new ApiResponse("user not exit"));
        }else if (userService.buyProductDirectly(userID,productID,merchantID) == 6) {
            return ResponseEntity.status(400).body(new ApiResponse("not found product"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("bad request"));
    }

    @GetMapping("/recommendations/{userID}")
    public ResponseEntity recommendations(@PathVariable String userID) {
        if (userService.recommendations(userID) != null) {
            return ResponseEntity.status(200).body(userService.recommendations(userID));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no recommendations"));
    }

    @GetMapping("/login/{userName}/{password}")
    public ResponseEntity login(@PathVariable String userName,@PathVariable String password){
        if (userService.login(userName,password)){
            return ResponseEntity.status(200).body(new ApiResponse("login success"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("login failed"));
    }
    @PutMapping("/mergeTowCategory/{userID}/{categoryId1}/{categoryId2}")
    public ResponseEntity mergeTowCategory(@PathVariable String userID, @PathVariable String categoryId1,@PathVariable String categoryId2){
        if (userService.mergeTowCategory(userID,categoryId1,categoryId2) == 1){
            return ResponseEntity.status(200).body(new ApiResponse("All products from the second category have been moved to the first category, and the second category has been deleted"));
        } else if (userService.mergeTowCategory(userID,categoryId1,categoryId2) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("First category not exit"));
        }else if (userService.mergeTowCategory(userID,categoryId1,categoryId2) == 0){
            return ResponseEntity.status(400).body(new ApiResponse("can not merge category because user not admin"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Second Category not exit"));
    }
    @GetMapping("/burByPoint/{userID}/{productID}")
    public ResponseEntity burByPoint(@PathVariable String userID,@PathVariable String productID){
        return ResponseEntity.status(200).body(userService.burByPoint(userID,productID));
    }


}
