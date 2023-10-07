/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.PromotionFooditems;
import com.nmhieu.repository.PromotionFoodItemsRepository;
import com.nmhieu.service.PromotionFoodItemsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class PromotionFoodItemsServiceImpl implements PromotionFoodItemsService{
    
    @Autowired
    private PromotionFoodItemsRepository promotion_FoodItemRepo;

    @Override
    public List<PromotionFooditems> getPromotion_FoodItemByIdpromotion(Map<String, String> params) {
        return this.promotion_FoodItemRepo.getPromotion_FoodItemByIdpromotion(params);
    }

    @Override
    public PromotionFooditems getPromotion_FoodItemById(int id) {
        return this.promotion_FoodItemRepo.getPromotion_FoodItemById(id);
    }

    @Override
    public boolean deletePromotion_FoodItem(int id) {
        return this.promotion_FoodItemRepo.deletePromotion_FoodItem(id);
    }
    
}
