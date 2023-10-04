/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.PromotionFooditems;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
public interface FoodItemsService {

    List<Fooditems> getFoodItems(Map<String, String> params);

    boolean addOrUpdateFoodItem(Fooditems foodItem);

    Fooditems getFoodItemById(int id);

    boolean delFoodItem(int id);

    List<Fooditems> getAllFoodItem();

    int countFoodItems(Map<String, String> params);

    List<Fooditems> getFoodItemsByCategoryId(int cateId);

    List<Fooditems> getFoodItemsByShelflifeId(int shelflifeId);
    
    boolean addOrUpdateFoodItem(Map<String, String> params, MultipartFile avatar);
    
    List<Object> getFoodItemsAndPromotion(Map<String, String> params);
    
    List<PromotionFooditems> getFoodAndPromotion(Map<String, String> params);
}
