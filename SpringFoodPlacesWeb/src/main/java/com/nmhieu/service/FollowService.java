/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.Follow;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface FollowService {
    int addFollow (Map<String, String> params);
    Follow getFollowByUserIdAndRestaurantId (int userId, int restaurantId);
}
