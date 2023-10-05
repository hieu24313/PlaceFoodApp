/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.PromotionType;
import com.nmhieu.repository.PromotionTypeRepository;
import com.nmhieu.service.PromotionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class PromotionTypeServiceImpl implements PromotionTypeService{

    @Autowired
    private PromotionTypeRepository promotionTypeRepo;
    @Override
    public PromotionType getPromotionTypeById(int id) {
        return this.promotionTypeRepo.getPromotionTypeById(id);
    }
    
}
