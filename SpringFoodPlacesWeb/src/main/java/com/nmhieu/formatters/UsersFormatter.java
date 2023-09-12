/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.formatters;

import com.nmhieu.pojo.Users;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author HP
 */
public class UsersFormatter implements Formatter<Users>{
    @Override
    public String print(Users user, Locale locale) {
        return String.valueOf(user.getUserId());
    }

    @Override
    public Users parse(String userId, Locale locale) throws ParseException {
        return new Users(Integer.parseInt(userId));
    }
}
