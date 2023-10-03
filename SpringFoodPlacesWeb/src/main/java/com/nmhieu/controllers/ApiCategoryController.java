/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.service.CategoriesFoodService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api")
public class ApiCategoryController {

    @Autowired
    private CategoriesFoodService categoryFoodSer;

    @DeleteMapping("/restaurantManager/categoriesFood/newCategoriesFood/{categoryfoodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "categoryfoodId") int id) {
        this.categoryFoodSer.delCategory(id);
    }

    @GetMapping("/restaurantManager/categories/")
    @CrossOrigin
    public ResponseEntity<List<CategoriesFood>> list(@RequestParam Map<String, String> params) {
        
        return new ResponseEntity<>(this.categoryFoodSer.getCategoriesFood(params), HttpStatus.OK);
    }
//    @GetMapping("/categories/")
//    @CrossOrigin
//    public ResponseEntity<List<Object[]>> list(@RequestParam Map<String, String> params) {
//        List<Object[]> categoriesList = this.categoryFoodSer.getCategoriesFood(params);
//        return ResponseEntity.ok(categoriesList);
//    }
    
    @DeleteMapping("/server/restaurantManager/categoriesFood/newCategoriesFood/{categoryfoodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_no_token(@PathVariable(value = "categoryfoodId") int id) {
        this.categoryFoodSer.delCategory(id);
    }
    
    @GetMapping("/categories/")
    @CrossOrigin
    public ResponseEntity<List<CategoriesFood>> listCategory(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.categoryFoodSer.getCategoriesFood(params), HttpStatus.OK);
    }
    
    @PostMapping("/restaurantManager/add-or-update-category/")
    @CrossOrigin
    public ResponseEntity<String> addOrUpdateCate(@RequestBody Map<String, String> params){
        boolean check = this.categoryFoodSer.addOrUpdateCate(params);
        if(check){
            return new ResponseEntity<>("Thành công!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Không thành công!", HttpStatus.BAD_REQUEST);
        }
    }
}
