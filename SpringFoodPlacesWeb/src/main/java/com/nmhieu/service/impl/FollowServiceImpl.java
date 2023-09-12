/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.Follow;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.FollowRepository;
import com.nmhieu.repository.RestaurantsRepository;
import com.nmhieu.repository.UsersRepository;
import com.nmhieu.service.FollowService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepo;

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private RestaurantsRepository restaurantsRepo;

    @Override
    public int addFollow(Map<String, String> params) {
        Follow follow = new Follow();
        String userId = params.get("userId");
        String restaurantId = params.get("restaurantId");

        if (userId != null && !userId.isEmpty()) {
            Users user = this.usersRepo.getUserById(Integer.parseInt(userId));
            if (user != null) {
                follow.setUserIdIndex(user);
            } else {
                return 0;
            }
        } else {
            return 0;
        }

        if (restaurantId != null && !restaurantId.isEmpty()) {
            Restaurants restaurant = this.restaurantsRepo.getRestaurantById(Integer.parseInt(restaurantId));
            if (restaurant != null) {
                follow.setRestaurantIdIndex(restaurant);
            } else {
                return 0;
            }
        } else {
            return 0;
        }

        return this.followRepo.addFollow(follow);
    }

    @Override
    public Follow getFollowByUserIdAndRestaurantId(int userId, int restaurantId) {
        return this.followRepo.getFollowByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Override
    public Follow checkFollow(Map<String, String> params) {
//        Follow follow = new Follow();
        String userId = params.get("userId");
        String restaurantId = params.get("restaurantId");
        
       

        return this.followRepo.checkFollow( this.getFollowByUserIdAndRestaurantId(Integer.parseInt(userId), Integer.parseInt(restaurantId)));
    }

    @Override
    public List<Follow> getFollowByRestaurantId(int restaurantId) {
        return this.followRepo.getFollowByRestaurantId(restaurantId);
    }

}
