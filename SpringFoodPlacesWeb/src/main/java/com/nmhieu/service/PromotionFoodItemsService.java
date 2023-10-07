/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.PromotionFooditems;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface PromotionFoodItemsService {
    List<PromotionFooditems> getPromotion_FoodItemByIdpromotion(Map<String, String> params);
    PromotionFooditems getPromotion_FoodItemById(int id);
    boolean deletePromotion_FoodItem(int id);
}
