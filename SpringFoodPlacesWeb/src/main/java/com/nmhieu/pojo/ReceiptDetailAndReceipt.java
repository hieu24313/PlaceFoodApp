/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author HP
 */
@Data
public class ReceiptDetailAndReceipt {

    private int receiptId;
    private String foodName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal amount;
    private Date createdDate;
    private int statusReceiptId;
    private String statusReceipt;
    private String location;
}
