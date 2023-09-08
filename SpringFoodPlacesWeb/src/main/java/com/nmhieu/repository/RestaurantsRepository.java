/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Restaurants;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface RestaurantsRepository {
    List<Restaurants> getRestaurants(Map<String, String> params);
    boolean addOrUpdateRestaurants(Restaurants restaurant);
    Restaurants getRestaurantById(int id);
    boolean deleteRestaurants(int id);
    int countRestaurants(Map<String, String> params);
    List<Restaurants> getRestaurantByUserId(int userId);
    Restaurants registerRestaurant(Restaurants restaurant);
    
//    List<Object[]> getRestaurantsNotConfirm(Map<String, String> params);
}
