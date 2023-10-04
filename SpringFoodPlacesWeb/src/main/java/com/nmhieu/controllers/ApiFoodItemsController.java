/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.service.FoodItemsService;
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
import org.springframework.web.bind.annotation.RequestBody;
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
public class ApiFoodItemsController {

    @Autowired
    private FoodItemsService foodItemsSer;

    @DeleteMapping("/restaurantManager/foodItems/{foodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "foodId") int id) {
        this.foodItemsSer.delFoodItem(id);
    }

    @GetMapping("/restaurantManager/foodItems/")
    @CrossOrigin
    public ResponseEntity<List<Fooditems>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.foodItemsSer.getFoodItems(params), HttpStatus.OK);
    }

    @DeleteMapping("/server/restaurantManager/foodItems/{foodId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_no_token(@PathVariable(value = "foodId") int id) {
        this.foodItemsSer.delFoodItem(id);
    }

    @GetMapping(path = "/foodItems/{foodId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Fooditems> detail(@PathVariable(value = "foodId") int foodId) {
        return new ResponseEntity<>(this.foodItemsSer.getFoodItemById(foodId), HttpStatus.OK);
    }
    
    @GetMapping("/foodItems/")
    @CrossOrigin
    public ResponseEntity<List<Fooditems>> listFood(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.foodItemsSer.getFoodItems(params), HttpStatus.OK);
    }
    
    @PostMapping("/restaurantManager/add-or-update-fooditem/")
    @CrossOrigin
    public ResponseEntity<String> addOrUpdateFoodItem(@RequestBody Map<String,String> params, @RequestPart MultipartFile avatar){
        boolean check = this.foodItemsSer.addOrUpdateFoodItem(params, avatar);
        if(check){
            return new ResponseEntity<>("Thành công!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Có lỗi xảy ra!", HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/restaurantManager/fooditems-promotion/")
    @CrossOrigin
    public ResponseEntity<List<Object>> getFoodAndPromotion(@RequestParam Map<String, String> params){
        return new ResponseEntity<>(this.foodItemsSer.getFoodItemsAndPromotion(params), HttpStatus.OK);
    }
    
//    @GetMapping("/foodItems/{foodId}/")
//    @CrossOrigin
//    public ResponseEntity<Fooditems> foodItem(@PathVariable(value = "foodId") int foodid) {
//        return new ResponseEntity<>(this.foodItemsSer.getFoodItemById(foodid), HttpStatus.OK);
//    }
}
