/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.Promotion;
import com.nmhieu.pojo.PromotionFooditems;
import com.nmhieu.repository.FoodItemsRepository;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.FoodItemsService;
import com.nmhieu.service.PromotionFoodItemsService;
import com.nmhieu.service.PromotionService;
import com.nmhieu.service.RestaurantsService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.velocity.exception.ParseErrorException;
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

    @Autowired
    private PromotionFoodItemsService promotion_FoodService;

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
        String cateId = params.get("categoryfoodId");
        if (cateId != null) {
            System.out.println(cateId);
            foodItem.setCategoryfoodId(this.cateService.getCategoryById(Integer.parseInt(cateId)));
        }
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
        String[] numbers1 = PromotionId.split(",");
        int idFood = Integer.parseInt(foodId);
        Map<String, String> params1 = new HashMap<>();
        params1.put("foodId", String.format(foodId));
        for (String proId : numbers1) {
            if (!proId.isEmpty() && !"".equals(proId) && proId != null) {
                System.out.println(proId);
                params1.put("promotionId", String.format(proId));
                System.out.println(this.promotion_FoodService.getPromotion_FoodItemByIdpromotion(params1));
                if (this.promotion_FoodService.getPromotion_FoodItemByIdpromotion(params1).isEmpty()) {

                    this.promotionService.addPromotionForFood(idFood, Integer.parseInt(proId));
                }
            }
        }
        if (foodId != null && !foodId.isEmpty()) {
            Fooditems f = this.getFoodItemById(Integer.parseInt(foodId));
            if (avatar.isEmpty()) {
                foodItem.setAvatar(f.getAvatar());
            }
            foodItem.setAvailable(f.getAvailable());
            foodItem.setShelflifeId(f.getShelflifeId());
            foodItem.setActive(Boolean.TRUE);
            foodItem.setFoodId(Integer.valueOf(foodId));
            return this.foodItemRepo.updateFoodItem(foodItem);
        } else {
            return this.foodItemRepo.addFoodItem(foodItem);
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

    @Override
    public List<Fooditems> getFoodAfterPromotion(Map<String, String> params) {
        //restaurantId
//        List<Promotion> listPromotions = this.promotionService.getPromotionWithDate(params);
        List<Fooditems> listFoodItems = this.foodItemRepo.getFoodItems(params);
        List<Fooditems> listFoodFinal = new ArrayList<>();

        List<PromotionFooditems> listPromotionAndFoodItem = this.promotion_FoodService.getPromotion_FoodItemByIdpromotion(params);
//        for(Promotion p: listPromotions){
//            if()
//        }
//        List<Fooditems> listFoodFinal = listFoodItems.stream().filter(f -> {
//            params.put("foodId", f.getFoodId());
//            List<PromotionFooditems> listPromotionFood = this.promotion_FoodService.getPromotion_FoodItemByIdpromotion(params);
//            for (PromotionFooditems pf : listPromotionFood) {
//                if (Objects.equals(pf.getFoodId().getFoodId(), f.getFoodId())) {
//                    Promotion p = this.promotionService.getPromotionByIdWithDate(pf.getPromotionId().getPromotionId());
//                    if (p != null) {
//                        if (f.getOldPrice() == null) {
//                            f.setOldPrice(f.getPrice());
//                        }
////                    f.setPrice(f.getPrice());
//                        if (p.getPromotionTypeId().getPromotionTypeId() == 1) {
//                            BigDecimal percent = new BigDecimal(Long.parseLong(p.getPricePromotion()) / 100);
//                            f.setPrice(f.getPrice().subtract(f.getPrice().multiply(percent)));
//                        } else {
//                            BigDecimal PromoPrice = new BigDecimal(p.getPricePromotion());
//                            f.setPrice(f.getPrice().subtract(PromoPrice));
//                        }
//                    }
//                }
//            }
//            return f;
//        }).collect(Collectors.toList());

        for (Fooditems f : listFoodItems) {
            params.put("foodId", String.valueOf(f.getFoodId()));
            List<PromotionFooditems> listPromotionFood = this.promotion_FoodService.getPromotion_FoodItemByIdpromotion(params);
            for (PromotionFooditems pf : listPromotionFood) {
                if (Objects.equals(pf.getFoodId().getFoodId(), f.getFoodId())) {
                    Promotion p = this.promotionService.getPromotionByIdWithDate(pf.getPromotionId().getPromotionId());
                    if (p != null) {
                        if (f.getOldPrice() == null) {
                            f.setOldPrice(f.getPrice());
                        }
//                    f.setPrice(f.getPrice());
                        if (p.getPromotionTypeId().getPromotionTypeId() == 1) { //giảm theo %
                            BigDecimal percent = new BigDecimal(Long.parseLong(p.getPricePromotion()) / 100);
                            BigDecimal kq = f.getPrice().subtract(f.getPrice().multiply(percent));
                            if (kq.compareTo(BigDecimal.ZERO) >= 0) {
                                f.setPrice(kq);
                            } else {
                                f.setPrice(BigDecimal.ZERO);
                            }
                            
                        } else { //giảm theo giá tiền
                            BigDecimal PromoPrice = new BigDecimal(p.getPricePromotion());

                            BigDecimal kq = f.getPrice().subtract(PromoPrice);
                            if (kq.compareTo(BigDecimal.ZERO) >= 0) {
                                f.setPrice(kq);
                            } else {
                                f.setPrice(BigDecimal.ZERO);
                            }
                        }
                    }
                }
            }
            listFoodFinal.add(f);
        }
        return listFoodFinal;
    }

}
