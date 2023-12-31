///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nmhieu.components;
//
//import com.twilio.Twilio;
//import com.twilio.exception.ApiConnectionException;
//import com.twilio.exception.ApiException;
//import com.twilio.rest.verify.v2.service.Verification;
//import com.twilio.rest.verify.v2.service.VerificationCheck;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author HP
// */
//@Component
//@PropertySource("classpath:configs.properties")
//public class TwillioService {
//    
//    @Autowired
//    private Environment env;
//    
//    public final String ACCOUNT_SID = env.getProperty("twillio.ACCOUNT_SID");
//    public final String AUTH_TOKEN = env.getProperty("twillio.AUTH_TOKEN");
//    public final String VERYFICATION_SID_SERVICE = env.getProperty("twillio.VERYFICATION_SERVICE_SID");
//    
//    public TwillioService() {
//    }
//
//    public boolean sendMessage(String phoneNumber) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        try{
//            //gửi code
//            Verification very = Verification.creator(
//                    VERYFICATION_SID_SERVICE,
//                    phoneNumber,
//                    "sms").create();
//            return true;
//        }catch(ApiException e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    public String auth_OTP(String OTP, String phoneNumber){
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        String auth = null;
//        try{
//            //xác minh code đúng hay không
////            String code = "650113"; // code người dùng nhập vào
//            VerificationCheck verificationCheck = VerificationCheck.creator(
//                VERYFICATION_SID_SERVICE)
//            .setTo(phoneNumber)
//            .setCode(OTP)
//            .create();
//            auth = verificationCheck.getStatus();
//        }catch(ApiException e){
//            e.printStackTrace();
//        }
//        return auth;
//    }
//
//}
