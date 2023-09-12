/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.nmhieu.repository.StatsRepository;
import com.nmhieu.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class StatsServiceImpl implements StatsService{

    @Autowired
    private StatsRepository statsRepo;
    
    @Override
    public List<Object[]> statsRevenue(Map<String, String> params) {
        return this.statsRepo.statsRevenue(params);
    }

    @Override
    public List<Object[]> statsRevenueByCate(Map<String, String> params) {
        return this.statsRepo.statsRevenueByCate(params);
    }

    @Override
    public List<Object[]> statsRestaurant(Map<String, String> params) {
        return this.statsRepo.statsRestaurant(params);
    }
    
}
