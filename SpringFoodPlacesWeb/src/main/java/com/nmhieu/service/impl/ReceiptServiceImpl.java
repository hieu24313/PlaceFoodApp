/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.pojo.Cart;
import com.nmhieu.pojo.ReceiptStatus;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.repository.ReceiptRepository;
import com.nmhieu.service.ReceiptService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private ReceiptRepository receiptRepo;
    
    @Override
    public boolean addReceipt(Map<String, Cart> carts) {
        return this.receiptRepo.addReceipt(carts);
    }

    @Override
    public List<Receipts> getReceipts(Map<String, String> params) {
        return this.receiptRepo.getReceipts(params);
    }

    @Override
    public Receipts getReceiptById(int id) {
        return this.receiptRepo.getReceiptById(id);
    }

    @Override
    public boolean updateAcceptReceipt(int receiptId) {
        Receipts receipt = this.receiptRepo.getReceiptById(receiptId);
        
        if (receipt != null) {
            receipt.setStatusReceiptId(new ReceiptStatus(2));
        }
        
        return this.receiptRepo.updateAcceptReceipt(receipt);
    }
}
