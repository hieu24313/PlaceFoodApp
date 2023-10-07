/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Promotion;
import com.nmhieu.service.PromotionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api")
public class ApiPromotionController {
    @Autowired
    private PromotionService promotionService;
    
    @GetMapping("/restaurantManager/promotions/")
    @CrossOrigin
    public ResponseEntity<List<Promotion>> getPromotion(@RequestParam Map<String, String> params){
        return new ResponseEntity<>(this.promotionService.getPromotion(params), HttpStatus.OK);
    }
    
    @PostMapping("/restaurantManager/add-or-update-promotion/")
    @CrossOrigin
    public ResponseEntity<String> addOrUpdatePromotion(@RequestBody Map<String, String> params){
        boolean check = this.promotionService.addOrUpdatePromotion(params);
        if(check){
            return new ResponseEntity<>("Thành công!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Có lỗi xảy ra!", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/restaurantManager/delete-promotion/{promotionId}")
    @CrossOrigin
    public ResponseEntity<String> deletePromotion(@PathVariable(value = "promotionId") int id){
        boolean check = this.promotionService.deletePromotion(id);
        if(check){
            return new ResponseEntity<>("Xóa thành công!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Có lỗi xảy ra!", HttpStatus.BAD_REQUEST);
        }
    }
}
