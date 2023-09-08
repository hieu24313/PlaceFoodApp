/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.ShelfLifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api")
public class ApiShelfLifeController {
    @Autowired
    private ShelfLifeService shelfLifeSer;
    
    @DeleteMapping("/restaurantManager/shelfLife/newShelfLife/{shelflifeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "shelflifeId") int id){
        this.shelfLifeSer.delShelf(id);
    }
    
    @DeleteMapping("/server/restaurantManager/shelfLife/newShelfLife/{shelflifeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete_no_token(@PathVariable(value = "shelflifeId") int id){
        this.shelfLifeSer.delShelf(id);
    }
}
