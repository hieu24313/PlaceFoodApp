/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.Promotion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface PromotionService {
    List<Promotion> getPromotion(Map <String, String> params);
    List<Promotion> getPromotionWithDate(Map <String, String> params);
    Promotion getPromotionById(int id);
    Promotion getPromotionByIdWithDate(int id);
    boolean addPromotionForFood(int idFood, int idPromotion);
    boolean addOrUpdatePromotion(Map<String, String> params);
    boolean deletePromotion(int id);
}
