/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.twillio;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
@PropertySource("classpath:configs.properties")
public class TwilioManager {
    @Autowired
    private Environment env;
    
    public final String VERYFICATION_SID_SERVICE = env.getProperty("twillio.VERYFICATION_SERVICE_SID");
    
    @Autowired
    public TwilioManager() {
    }
    
    public boolean sendMessage(String phoneNumber) {
        try{
            //gửi code
            Verification very = Verification.creator(
                    VERYFICATION_SID_SERVICE,
                    phoneNumber,
                    "sms").create();
            return true;
        }catch(ApiException e){
            e.printStackTrace();
            return false;
        }
    }
    
//    public String auth_OTP(String OTP, String phoneNumber){
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


}
