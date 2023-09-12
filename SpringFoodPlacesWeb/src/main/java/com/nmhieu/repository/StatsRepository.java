/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface StatsRepository {
    List<Object[]> statsRevenue(Map<String, String> params);
    List<Object[]> statsRevenueByCate(Map<String, String> params);
    List<Object[]> statsRestaurant(Map<String, String> params);
}
