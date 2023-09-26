/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Cart;
import com.nmhieu.pojo.Receipts;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface ReceiptRepository {
    boolean addReceipt(Map<String, Cart> carts);
    boolean addReceiptNoUser(Map<String, Cart> carts);
    List<Receipts> getReceipts(Map<String, String> params);
    Receipts getReceiptById(int id);
    boolean updateAcceptReceipt(Receipts receipt);
    
}
