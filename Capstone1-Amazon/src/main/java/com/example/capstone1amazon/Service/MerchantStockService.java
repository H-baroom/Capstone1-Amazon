package com.example.capstone1amazon.Service;

import com.example.capstone1amazon.Model.Merchant;
import com.example.capstone1amazon.Model.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList();
    //private final UserService userService;
    private final ProductService productService;
    private final MerchantService merchantService;

    public int addMerchants(MerchantStock merchantStock) {
        for (int i = 0; i < productService.products.size(); i++) {
            if (productService.products.get(i).getId().equals(merchantStock.getProductID())) {
                for (int j = 0; j < merchantService.merchants.size(); j++) {
                    if (merchantService.merchants.get(j).getId().equals(merchantStock.getMarchantID())) {
                        merchantStocks.add(merchantStock);
                        return 1;
                    }
                }
                return 2;
            }
        }
        return 3;
    }
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean updateMerchantStock(String id,MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean merchantAddMoreStock(String productID, String marchantID,int amountOfAdditionalStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getProductID().equals(productID)) {
                if (merchantStocks.get(i).getMarchantID().equals(marchantID)) {
                    merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()+amountOfAdditionalStock);
                    return true;
                }
            }
        }
        return false;
    }

    //Third extra endpoint
    public String getMerchantTotalStockAndPrice(String merchantID) {
        int totalPrice=0;
        int totalstock=0;
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMarchantID().equals(merchantID)) {
                for (int j = 0; j < productService.products.size(); j++) {
                    if (productService.products.get(j).getId().equals(merchantStocks.get(i).getProductID())) {
                        totalPrice+=productService.products.get(j).getPrice() * merchantStocks.get(i).getStock();
                        totalstock+=merchantStocks.get(i).getStock();
                    }
                }
            }
        }
        if (totalPrice != 0 && totalstock != 0) {
            return "The stock value for this merchant ID :" + merchantID + " is " + totalPrice + ", which includes " + totalstock + " items";
        }else {
            return "This merchant ID :" + merchantID + " does not have any products in stock right now or not exit";
        }

    }


    public String transferStockBetweenMerchants(String sourceMerchantID, String destinationMerchantID, String productID, int quantity) {
        int sourceStock=0;
        boolean sourceFound = false;
        boolean destinationFound = false;

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMarchantID().equals(sourceMerchantID)) {
                if (merchantStocks.get(i).getProductID().equals(productID)) {
                    sourceFound =true;
                    sourceStock+=merchantStocks.get(i).getStock();
                }
            }
        }
        if (!sourceFound) {
            return "The product does not exist in the source merchant stock";
        }
        if (sourceStock >= quantity) {
            for (int i = 0; i < merchantStocks.size(); i++) {
                if (merchantStocks.get(i).getMarchantID().equals(destinationMerchantID)) {
                    if (merchantStocks.get(i).getProductID().equals(productID)) {
                        destinationFound =true;
                        merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()+quantity);
                        for (int j = 0; j < merchantStocks.size(); j++) {
                            if (merchantStocks.get(j).getMarchantID().equals(sourceMerchantID)) {
                                if (merchantStocks.get(j).getProductID().equals(productID)) {
                                    merchantStocks.get(j).setStock(merchantStocks.get(j).getStock()-quantity);
                                    return "The stock transfer has been completed successfully";
                                }
                            }
                        }
                    }
                }
            }
        }else {
            return "The quantity you requested is not available";
        }
        if (!destinationFound) {
            return "The product does not exist in the destination merchant stock";
        }
        return "Invalid request.";
    }



    }
