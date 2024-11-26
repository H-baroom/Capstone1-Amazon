package com.example.capstone1amazon.Service;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public ArrayList<Product> getProduct(){
        return products;
    }

    public boolean addProduct(Product product){
        for (int i = 0; i < categoryService.categories.size(); i++) {
            if (categoryService.categories.get(i).getId().equals(product.getCategoryID())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }


    //Fourth extra endpoint
    /*
    public int mergeTowCategory(String userID,String categoryId1,String categoryId2){
        boolean category1 = false;
        boolean category2 = false;
        for (int i = 0; i < categoryService.categories.size(); i++) {
            if (categoryService.categories.get(i).getId().equals(categoryId1)){
                category1=true;
            }
        }
        for (int i = 0; i < categoryService.categories.size(); i++) {
            if (categoryService.categories.get(i).getId().equals(categoryId2)){
                category2=true;
            }
        }
        for (int i = 0; i < userService.users.size(); i++) {

        }
        if (category1){
            if (category2) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getCategoryID().equals(categoryId2)) {
                        products.get(i).setCategoryID(categoryId1);
                    }
                }
                for (int i = 0; i < categoryService.categories.size(); i++) {
                    if (categoryService.categories.get(i).getId().equals(categoryId2)){
                        categoryService.categories.remove(i);
                    }
                }
                return 1;
            }
            return 3;
        }
        return 2;
    }
     */

}
