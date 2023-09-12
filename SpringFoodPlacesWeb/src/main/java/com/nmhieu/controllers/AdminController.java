/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.service.RestaurantsService;
import com.nmhieu.service.StatsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HP
 */

@Controller
@ControllerAdvice
public class AdminController {
    
    @Autowired
    private RestaurantsService restaurantService;
    
    @Autowired
    private StatsService statsService;
    
    @ModelAttribute
    public void commonAttr(Model model) {
        
    }
    
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/admin/stats")
    public String stats(Model model, @RequestParam Map<String, String> params) {
//        params.put("restaurantId", "19");
        
        model.addAttribute("statsRestaurant", this.statsService.statsRestaurant(params));
        return "stats";
    }
}
