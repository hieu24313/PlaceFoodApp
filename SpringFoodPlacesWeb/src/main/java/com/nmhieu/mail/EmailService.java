/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.mail;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
@PropertySource("classpath:configs.properties")
public class EmailService {
    
    private EmailManager emailManager;
    
    public EmailService(EmailManager emailManager){
        this.emailManager = emailManager;
    }
    
    public boolean send_Email(String toEmail, String contentEmail, String subjectEmail){
        return this.emailManager.send_Email(toEmail, contentEmail, subjectEmail);
    }
            
}
