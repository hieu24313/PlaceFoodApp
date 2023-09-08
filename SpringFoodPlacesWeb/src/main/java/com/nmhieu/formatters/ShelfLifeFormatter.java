/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.formatters;

import com.nmhieu.pojo.ShelfLife;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author HP
 */
public class ShelfLifeFormatter implements Formatter<ShelfLife>{
    @Override
    public String print(ShelfLife shelfLife, Locale locale) {
        return String.valueOf(shelfLife.getShelflifeId());
    }

    @Override
    public ShelfLife parse(String ShelfLifeId, Locale locale) throws ParseException {
        return new ShelfLife(Integer.parseInt(ShelfLifeId));
    }
}
