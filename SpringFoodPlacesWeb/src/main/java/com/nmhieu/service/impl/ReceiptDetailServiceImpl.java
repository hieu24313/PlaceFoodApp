/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.repository.ReceiptDetailRepository;
import com.nmhieu.service.ReceiptDetailService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService{

    @Autowired
    private ReceiptDetailRepository receiptDetailRepo;
    
    @Override
    public List<ReceiptDetail> getReceiptDetails(int receiptId) {
        return this.receiptDetailRepo.getReceiptDetails(receiptId);
    }

    @Override
    public List<ReceiptDetail> getReceiptDetailsByFoodId(int foodId) {
        return this.receiptDetailRepo.getReceiptDetailsByFoodId(foodId);
    }

    @Override
    public List<Object> getNowReceiptByJoinFoodAndReceiptAndReceiptDetail(Map<String, String> params) {
        return this.receiptDetailRepo.getNowReceiptByJoinFoodAndReceiptAndReceiptDetail(params);
    }
    
}
