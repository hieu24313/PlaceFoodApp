/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.repository.FoodItemsRepository;
import com.nmhieu.service.FoodItemsService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class FoodItemsServiceImpl implements FoodItemsService {

    @Autowired
    private FoodItemsRepository foodItemRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Fooditems> getFoodItems(Map<String, String> params) {
        return this.foodItemRepo.getFoodItems(params);
    }

    @Override
    public boolean addOrUpdateFoodItem(Fooditems foodItem) {
        foodItem.setAvailable(Boolean.TRUE);

        if (!foodItem.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(foodItem.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                foodItem.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(RestaurantsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return this.foodItemRepo.addOrUpdateFoodItem(foodItem);
    }

    @Override
    public Fooditems getFoodItemById(int id) {
        return this.foodItemRepo.getFoodItemById(id);
    }

    @Override
    public boolean delFoodItem(int id) {
        return this.foodItemRepo.delFoodItem(id);
    }

    @Override
    public List<Fooditems> getAllFoodItem() {
        return this.foodItemRepo.getAllFoodItem();
    }

    @Override
    public int countFoodItems(Map<String, String> params) {
        return this.foodItemRepo.countFoodItems(params);
    }

    @Override
    public List<Fooditems> getFoodItemsByCategoryId(int cateId) {
        return this.foodItemRepo.getFoodItemsByCategoryId(cateId);
    }

    @Override
    public List<Fooditems> getFoodItemsByShelflifeId(int shelflifeId) {
        return this.foodItemRepo.getFoodItemsByShelflifeId(shelflifeId);
    }

}
