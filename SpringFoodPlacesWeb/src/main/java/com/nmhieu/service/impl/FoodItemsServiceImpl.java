/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.PromotionFooditems;
import com.nmhieu.repository.FoodItemsRepository;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.FoodItemsService;
import com.nmhieu.service.PromotionService;
import com.nmhieu.service.RestaurantsService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private CategoriesFoodService cateService;
    
    @Autowired
    private RestaurantsService restaurantService;
    
    @Autowired
    private PromotionService promotionService;

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

    @Override
    public boolean addOrUpdateFoodItem(Map<String, String> params, MultipartFile avatar) {
        Fooditems foodItem = new Fooditems();
        String foodId = params.get("foodId");
        foodItem.setFoodName(params.get("foodName"));
        BigDecimal price = new BigDecimal(params.get("price"));
        foodItem.setPrice(price);
        foodItem.setDescription(params.get("description"));
        foodItem.setCategoryfoodId(this.cateService.getCategoryById(Integer.parseInt(params.get("categoryfoodId"))));
        foodItem.setRestaurantId(this.restaurantService.getRestaurantById(Integer.parseInt(params.get("restaurantId"))));
        if (!avatar.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    foodItem.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        String PromotionId = params.get("promotion");
        if(PromotionId != null || PromotionId.isEmpty()){
            List<Integer> numbers = Arrays.stream(PromotionId.split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
            int idFood = Integer.parseInt(foodId);
            for (int proId : numbers){
                this.promotionService.addPromotionForFood(idFood, proId);
            }
        }
        
        if(foodId == null || foodId.isEmpty()){
            return this.foodItemRepo.addFoodItem(foodItem);
        }else{
            foodItem.setFoodId(Integer.valueOf(foodId));
            return this.foodItemRepo.updateFoodItem(foodItem);
        }
        
    }

    @Override
    public List<Object> getFoodItemsAndPromotion(Map<String, String> params) {
        return this.foodItemRepo.getFoodItemsAndPromotion(params);
    }

    @Override
    public List<PromotionFooditems> getFoodAndPromotion(Map<String, String> params) {
        return this.foodItemRepo.getFoodAndPromotion(params);
    }

}
