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
public class EmailManager {
    @Autowired
    private Environment env;

    @Autowired
    public EmailManager() {

    }

    public boolean send_Email(String toEmail, String contentEmail, String subjectEmail) {
        try {
            String fromEmail = env.getProperty("mail.FROME_MAIL");
            String password = env.getProperty("mail.PASSWORD_APP");

            Properties props = new Properties();
            props.put("mail.smtp.host", env.getProperty("mail.HOST"));
            props.put("mail.smtp.port", env.getProperty("mail.PORT"));
            props.put("mail.smtp.auth", env.getProperty("mail.AUTH"));
            props.put("mail.smtp.starttls.enable", env.getProperty("mail.ENABLE"));

            Session session = Session.getDefaultInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            String subject = subjectEmail;

            message.setSubject(subject);
//            String emailContent = "<html><body>";
//            emailContent += "<p style=\"font-family: Arial, sans-serif; font-size: 14px;\">Đây là nội dung email gửi từ ứng dụng Java.</p>";
//            emailContent += "</body></html>";
            String emailContent = contentEmail;
            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);

            System.out.println("Email đã được gửi thành công!");
            return true;

        } catch (MessagingException e) {
            return false;
        }
    }
}
