/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Cart;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.service.ReceiptService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api")
public class ApiReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/pay/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void addReceipt(@RequestBody Map<String, Cart> carts) {
        try {
            if (carts != null && !carts.isEmpty()) {
                // Xử lý dữ liệu carts ở đây
                this.receiptService.addReceipt(carts);
            } else {
                // In thông báo nếu carts là null hoặc rỗng
                System.out.println("Không có dữ liệu carts được gửi lên.");
            }
        } catch (Exception e) {
            // In thông báo nếu có lỗi xảy ra trong quá trình xử lý carts
            System.out.println("Đã xảy ra lỗi khi xử lý dữ liệu carts: " + e.getMessage());
        }
    }
    
    @PostMapping("/payNoUser/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void addReceiptNoUser(@RequestBody Map<String, Cart> carts) {
        try {
            if (carts != null && !carts.isEmpty()) {
                // Xử lý dữ liệu carts ở đây
                this.receiptService.addReceiptNoUser(carts);
            } else {
                // In thông báo nếu carts là null hoặc rỗng
                System.out.println("Không có dữ liệu carts được gửi lên.");
            }
        } catch (Exception e) {
            // In thông báo nếu có lỗi xảy ra trong quá trình xử lý carts
            System.out.println("Đã xảy ra lỗi khi xử lý dữ liệu carts: " + e.getMessage());
        }
    }
    
    @GetMapping("/receipts/")
    @CrossOrigin
    public ResponseEntity<List<Receipts>> listReceipt(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.receiptService.getReceipts(params), HttpStatus.OK);
    }
    
    @PostMapping(path = "/receipt/{receiptId}/acceiptReceipt/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> acceptReceipt(@PathVariable(value = "receiptId") int receiptId) {
        String message = "Có lỗi xảy ra!";
        Boolean check = this.receiptService.updateAcceptReceipt(receiptId);
        
        if (check == true) {
            message = "Xác nhận đã nhận hàng thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            message = "Có lỗi xảy ra!";
        }
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
