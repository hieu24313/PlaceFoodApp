/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Fooditems;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface FoodItemsRepository {
    List<Fooditems> getFoodItems(Map<String, String> params);
    boolean addOrUpdateFoodItem(Fooditems foodItem);
    Fooditems getFoodItemById(int id);
    boolean delFoodItem(int id);
    List<Fooditems> getAllFoodItem();  
    int countFoodItems(Map<String, String> params);
    List<Fooditems> getFoodItemsByCategoryId(int cateId);
    List<Fooditems> getFoodItemsByShelflifeId(int shelflifeId);
}
