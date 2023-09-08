/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Restaurants;
import com.nmhieu.service.RestaurantsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api")
public class ApiRestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @DeleteMapping("/restaurantManager/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "restaurantId") int id) {
        this.restaurantsService.deleteRestaurants(id);
    }

    @DeleteMapping("/admin/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_admin(@PathVariable(value = "restaurantId") int id) {
        this.restaurantsService.deleteRestaurants(id);
    }

    @DeleteMapping("/server/restaurantManager/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_no_token(@PathVariable(value = "restaurantId") int id) {
        this.restaurantsService.deleteRestaurants(id);
    }

    @DeleteMapping("/server/admin/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_admin_no_token(@PathVariable(value = "restaurantId") int id) {
        this.restaurantsService.deleteRestaurants(id);
    }

    @PostMapping(path = "/register-restaurant/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Restaurants> registerRestaurant(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        Restaurants restaurant = this.restaurantsService.registerRestaurant(params, avatar);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/restaurants/")
    @CrossOrigin
    public ResponseEntity<List<Restaurants>> listRestaurant(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.restaurantsService.getRestaurants(params), HttpStatus.OK);
    }
    
    @GetMapping("/restaurants/{restaurantId}/")
    @CrossOrigin
    public ResponseEntity<Restaurants> listRestaurant1(@PathVariable(value = "restaurantId") int restaurantId) {
        return new ResponseEntity<>(this.restaurantsService.getRestaurantById(restaurantId), HttpStatus.OK);
    }
}
