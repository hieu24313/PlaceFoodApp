/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.formatters;

import com.nmhieu.pojo.Roles;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author HP
 */
public class RolesFormatter implements Formatter<Roles>{

    @Override
    public String print(Roles role, Locale locale) {
        return String.valueOf(role.getRoleId());
    }

    @Override
    public Roles parse(String roleId, Locale locale) throws ParseException {
        return new Roles(Integer.parseInt(roleId));
    }
    
}
