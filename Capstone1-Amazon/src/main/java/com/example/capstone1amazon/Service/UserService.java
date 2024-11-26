package com.example.capstone1amazon.Service;

import com.example.capstone1amazon.ApiResponse.ApiResponse;
import com.example.capstone1amazon.Model.Product;
import com.example.capstone1amazon.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList();
    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    public void addUser(User user){
        user.setRegistrationDate(LocalDate.now());
        users.add(user);
    }
    public ArrayList<User> getUsers(){
        return users;
    }
    public boolean updateUser(String id,LocalDate uodateDateToTest ,User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                users.get(i).setRegistrationDate(uodateDateToTest);
                return true;
            }
        }
        return false;
    }
    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }
    public int  buyProductDirectly(String userID,String productID, String merchantID){
        int price =0;
        for (int i = 0; i < productService.products.size(); i++) {
            if (productService.products.get(i).getId().equals(productID)) {
                price = productService.products.get(i).getPrice();
            }
        }
        for (int i = 0; i < merchantStockService.merchantStocks.size(); i++) {
            if (merchantStockService.merchantStocks.get(i).getProductID().equals(productID)) {
                if (merchantStockService.merchantStocks.get(i).getMarchantID().equals(merchantID)) {
                    if (merchantStockService.merchantStocks.get(i).getStock()>=1) {
                        for (int j = 0; j < users.size(); j++) {
                            if (users.get(j).getId().equals(userID)) {
                                if (users.get(j).getBalance() >= price) {
                                    users.get(j).setBalance(users.get(j).getBalance()-price);
                                    merchantStockService.merchantStocks.get(i).setStock(merchantStockService.merchantStocks.get(i).getStock()-1);
                                    for (int k = 0; k < productService.products.size(); k++) {
                                        if (productService.products.get(k).getId().equals(productID)) {
                                            if (users.get(j).getBuyedProducts() == null) {
                                                users.get(j).setBuyedProducts(new ArrayList<Product>());
                                                users.get(j).addBuyedProduct(productService.products.get(k));
                                            }else {
                                                users.get(j).getBuyedProducts().add(productService.products.get(k));
                                            }
                                            addlateLoyaltyPoints(userID,productID);
                                            return 1;
                                        }
                                    }
                                }return 3;//balance not
                            }return 5;
                        }return 5;
                    }else {return 4;}
                }
                else {return 2;}
            }
        }
        return 6;
    }

    //First extra endpoint
    public ArrayList<Product> recommendations(String userID){
        ArrayList<Product> recommendations = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userID)) {
                for (int k = 0; k < users.get(i).getBuyedProducts().size(); k++) {
                    for (int j = 0; j < productService.products.size(); j++) {
                        if (productService.products.get(j).getCategoryID().equals(users.get(i).getBuyedProducts().get(k).getCategoryID())) {
                            recommendations.add(productService.products.get(j));
                        }
                    }
                }
            }
        }
        return recommendations;
    }
    //Second extra endpoint
    public void addlateLoyaltyPoints(String userID,String productID){
        int numberOfYears=0;
        int price =0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userID)) {
                numberOfYears = Period.between(users.get(i).getRegistrationDate(),LocalDate.now()).getYears();
                for (int j = 0; j < productService.products.size(); j++) {
                    if (productService.products.get(j).getId().equals(productID)) {
                        price = productService.products.get(j).getPrice();
                    }
                }
            }
        }
        if (numberOfYears > 7) {
            numberOfYears = 7;
        }
        int points = (int)((price / 10) * numberOfYears) ;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userID)) {
                users.get(i).setPoints(users.get(i).getPoints()+points);
            }
        }
    }

    // extra credit
    public boolean login(String userName,String password){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)) {
                if (users.get(i).getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

}
