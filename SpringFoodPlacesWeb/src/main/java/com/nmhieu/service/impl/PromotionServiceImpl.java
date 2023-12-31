/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.Promotion;
import com.nmhieu.repository.PromotionRepository;
import com.nmhieu.service.PromotionService;
import com.nmhieu.service.PromotionTypeService;
import com.nmhieu.service.RestaurantsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepo;

    @Autowired
    private SimpleDateFormat MY_DATE_FORMAT;

    @Autowired
    private RestaurantsService restaurantService;

    @Autowired
    private PromotionTypeService promotionTypeService;

    @Override
    public List<Promotion> getPromotion(Map<String, String> params) {
        return this.promotionRepo.getPromotion(params);
    }

    @Override
    public Promotion getPromotionById(int id) {
        return this.promotionRepo.getPromotionById(id);
    }

    @Override
    public boolean addPromotionForFood(int idFood, int idPromotion) {
        return this.promotionRepo.addPromotionForFood(idFood, idPromotion);
    }

    @Override
    public boolean addOrUpdatePromotion(Map<String, String> params) {
        Promotion promotion = new Promotion();
        String PromotionId = params.get("promotionId");
        String promotionName = params.get("promotionName");
        int restaurantId = Integer.parseInt(params.get("restaurantId"));
        promotion.setRestaurantId(this.restaurantService.getRestaurantById(restaurantId));
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = MY_DATE_FORMAT.parse(params.get("fromDate"));
            toDate = MY_DATE_FORMAT.parse(params.get("toDate"));
        } catch (ParseException ex) {
            Logger.getLogger(PromotionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String price = params.get("price");
        int typeId = Integer.parseInt(params.get("typePromotion"));
        promotion.setActive(Boolean.TRUE);
        promotion.setFromDate(fromDate);
        promotion.setToDate(toDate);
        promotion.setPromotionName(promotionName);
        promotion.setPricePromotion(price);
        promotion.setPromotionTypeId(this.promotionTypeService.getPromotionTypeById(typeId));
        if (PromotionId != null && !PromotionId.isEmpty()) {
            promotion.setPromotionId(Integer.valueOf(PromotionId));
        }
        return this.promotionRepo.addOrUpdatePromotion(promotion);
    }

    @Override
    public boolean deletePromotion(int id) {
        return this.promotionRepo.deletePromotion(id);
    }

    @Override
    public List<Promotion> getPromotionWithDate(Map<String, String> params) {
        List<Promotion> list = this.promotionRepo.getPromotion(params);
        Date ngayHienTai = new Date();
        List<Promotion> listPromotion = list;
        for (Promotion p : listPromotion) {
            if (p.getFromDate() != null) {
                if(ngayHienTai.before(p.getFromDate())){
                    list.remove(p);
                }
            }
            if (p.getToDate() != null) {
                if(ngayHienTai.after(p.getToDate())){
                    list.remove(p);
                }
            }

//            LocalDate toDate = LocalDate.of(2023, 12, 31);  // Ví dụ: ngày kết thúc là 31/12/2023
        }
        return list;
    }

    @Override
    public Promotion getPromotionByIdWithDate(int id) {
        Promotion p =  this.promotionRepo.getPromotionById(id);
        Date ngayHienTai = new Date();
        if (p.getFromDate() != null) {
                if(ngayHienTai.before(p.getFromDate())){
                    return null;
                }
            }
            if (p.getToDate() != null) {
                if(ngayHienTai.after(p.getToDate())){
                    return null;
                }
            }
            return p;
        
    }

}
