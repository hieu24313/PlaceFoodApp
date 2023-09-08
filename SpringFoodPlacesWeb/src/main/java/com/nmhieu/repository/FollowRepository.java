/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Follow;

/**
 *
 * @author HP
 */
public interface FollowRepository {
    int addFollow (Follow follow);
    Follow getFollowByUserIdAndRestaurantId (int userId, int restaurantId);
}
