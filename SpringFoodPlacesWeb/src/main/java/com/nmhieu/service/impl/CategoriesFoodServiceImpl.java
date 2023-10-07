/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.repository.CategoryFoodRepository;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.RestaurantsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class CategoriesFoodServiceImpl implements CategoriesFoodService{
    @Autowired
    private CategoryFoodRepository categoryFoodRepo;
    @Override
    public List<CategoriesFood> getCategoriesFood(Map<String, String> params) {
        return this.categoryFoodRepo.getCategoriesFood(params);
    }

    @Autowired
    private RestaurantsService restaurantService;
    
    @Override
    public int countCategoriesFood() {
        return this.categoryFoodRepo.countCategoriesFood();
    }

    @Override
    public boolean addOrUpdateCate(CategoriesFood cate) {
        return this.categoryFoodRepo.addOrUpdateCate(cate);
    }

    @Override
    public CategoriesFood getCategoryById(int id) {
        return this.categoryFoodRepo.getCategoryById(id);
    }

    @Override
    public boolean delCategory(int id) {
        return this.categoryFoodRepo.delCategory(id);
    }

    @Override
    public List<CategoriesFood> getCategoriesFoodByRestaurantId(int restaurantId) {
        return this.categoryFoodRepo.getCategoriesFoodByRestaurantId(restaurantId);
    }

    @Override
    public boolean addOrUpdateCate(Map<String, String> params) {
        String cateId = params.get("CategoryId");
        String cateName = params.get("categoryName");
        int restaurantId = Integer.parseInt(params.get("restaurantId"));
        CategoriesFood cate = new CategoriesFood();
        cate.setCategoryname(cateName);
        cate.setRestaurantId(this.restaurantService.getRestaurantById(restaurantId));
        if(cateId != null && !cateId.isEmpty()){
            cate.setCategoryfoodId(Integer.valueOf(cateId));
        }
        cate.setActive(Boolean.TRUE);
        return this.categoryFoodRepo.addOrUpdateCate(cate);
    }
    
}
