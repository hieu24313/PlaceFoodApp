/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.ReceiptDetail;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ReceiptDetailService {

    List<ReceiptDetail> getReceiptDetails(int receiptId);

    List<ReceiptDetail> getReceiptDetailsByFoodId(int foodId);
}
