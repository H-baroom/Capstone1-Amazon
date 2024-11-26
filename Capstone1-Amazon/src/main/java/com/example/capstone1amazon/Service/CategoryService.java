package com.example.capstone1amazon.Service;

import com.example.capstone1amazon.Controller.ProductController;
import com.example.capstone1amazon.Model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();



    public ArrayList<Category> getCategories(){
        return categories;
    }

    public void addCategories(Category category){
        categories.add(category);
    }

    public boolean updateCategory(String id,Category category){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    //I've completed this endpoint, but due to a circular error, I've had to comment it out.
    /*
    public int mergeTowCategory(String categoryId1,String categoryId2){
        boolean category1 = false;
        boolean category2 = false;
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId1)){
                category1=true;
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId2)){
                category2=true;
            }
        }
        if (category1){
            if (category2) {
                for (int i = 0; i < productController.getProduct().size(); i++) {
                    if (productController.getProduct().get(i).getCategoryID().equals(categoryId2)) {
                        productController.getProduct().get(i).setCategoryID(categoryId1);
                    }
                }
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getId().equals(categoryId2)){
                        categories.remove(i);
                    }
                }
            }
            return 3;
        }
        return 2;
    }
     */


}
