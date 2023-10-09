/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author HP
 */
public class ApiStatsController {
    @Autowired
    private StatsService statsSerVice;
    
    @GetMapping("/restaurantManager/statsRevenue/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getStatsRevenue(@RequestBody Map<String, String> params){
        return new ResponseEntity<>(this.statsSerVice.statsRevenue(params), HttpStatus.OK);
    }
    
    @GetMapping("/restaurantManager/getStatsRestaurant/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getStatsRestaurant(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.statsSerVice.statsRestaurant(params), HttpStatus.OK);
    }
    
    @GetMapping("/restaurantManager/getStatsRevenueByCate/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getStatsRevenueByCate(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.statsSerVice.statsRevenueByCate(params), HttpStatus.OK);
    }
}
