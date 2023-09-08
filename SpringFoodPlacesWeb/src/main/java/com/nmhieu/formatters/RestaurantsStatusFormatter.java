/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.formatters;

import com.nmhieu.pojo.RestaurantStatus;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Administrator
 */
public class RestaurantsStatusFormatter implements Formatter<RestaurantStatus>{

    @Override
    public String print(RestaurantStatus restaurantStatus, Locale locale) {
        return String.valueOf(restaurantStatus.getStatusId());
    }

    @Override
    public RestaurantStatus parse(String restaurantsStatusId, Locale locale) throws ParseException {
        return new RestaurantStatus(Integer.parseInt(restaurantsStatusId));
    }
    
}
