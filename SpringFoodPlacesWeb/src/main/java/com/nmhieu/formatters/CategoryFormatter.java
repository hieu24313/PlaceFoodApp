/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.formatters;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.ShelfLife;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author HP
 */
public class CategoryFormatter implements Formatter<CategoriesFood>{

    @Override
    public String print(CategoriesFood category, Locale locale) {
        return String.valueOf(category.getCategoryfoodId());
    }

    @Override
    public CategoriesFood parse(String categoriesFoodId, Locale locale) throws ParseException {
        return new CategoriesFood(Integer.parseInt(categoriesFoodId));
    }
    
}
