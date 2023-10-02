/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 *
 * @author HP
 */


public class EmailSender {
   public static void main(String[] args) throws UnsupportedEncodingException {
        String fromEmail = "hieu01659505026@gmail.com";
        String password = "oobbdhqqsetjtnkk";
        String toEmail = "trandangtuan0168@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            String subject = "<html><head><meta charset=\"UTF-8\"></head><body><p>Chào bạn</p></body></html>";

            message.setSubject(subject);
//            String emailContent = "<html><body>";
//            emailContent += "<p style=\"font-family: Arial, sans-serif; font-size: 14px;\">Đây là nội dung email gửi từ ứng dụng Java.</p>";
//            emailContent += "</body></html>";
            String emailContent = "hiếu gửi được email rồi";
            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);

            System.out.println("Email đã được gửi thành công!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

