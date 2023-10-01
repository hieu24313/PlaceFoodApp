/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
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
public class EmailService {
    
    private EmailManager emailManager;
    
    public EmailService(EmailManager emailManager){
        this.emailManager = emailManager;
    }
    
    public boolean send_Email(String toEmail, String contentEmail, String subjectEmail){
        return this.emailManager.send_Email(toEmail, contentEmail, subjectEmail);
    }
            
}
