package com.example.capstone1amazon.Controller;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.Merchant;
import com.example.capstone1amazon.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchant() {
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }
    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchants(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.updateMerchant(id, merchant)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        if (merchantService.deleteMerchant(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}
