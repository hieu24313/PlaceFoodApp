///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nmhieu.twillio;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author HP
// */
//@Service
//public class TwillioService {
//    private final TwilioManager twilioManager;
//
//    @Autowired
//    public TwillioService(TwilioManager twilioManager) {
//        this.twilioManager = twilioManager;
//    }
//    
//    public Boolean send_OTP(String phoneNumber){
//        return twilioManager.sendMessage(phoneNumber);
//    }
//    
////    public String send_OTP(String OTP, String phoneNumber){
////        return twilioManager.auth_OTP(OTP, phoneNumber);
////    }
//}
