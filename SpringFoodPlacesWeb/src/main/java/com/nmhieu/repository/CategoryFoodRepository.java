/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.CategoriesFood;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface CategoryFoodRepository {
    List<CategoriesFood> getCategoriesFood(Map<String, String> params);
    int countCategoriesFood();
    boolean addOrUpdateCate(CategoriesFood cate);
    CategoriesFood getCategoryById(int id);
    boolean delCategory(int id);
    List<CategoriesFood> getCategoriesFoodByRestaurantId(int restaurantId);
}
