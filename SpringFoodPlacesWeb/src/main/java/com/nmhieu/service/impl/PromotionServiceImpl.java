/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.Promotion;
import com.nmhieu.repository.PromotionRepository;
import com.nmhieu.service.PromotionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class PromotionServiceImpl implements PromotionService{

    @Autowired
    private PromotionRepository promotionRepo;
    
    @Override
    public List<Promotion> getPromotion(Map<String, String> params) {
        return this.promotionRepo.getPromotion(params);
    }

    @Override
    public Promotion getPromotionById(int id) {
        return this.promotionRepo.getPromotionById(id);
    }
    
}
