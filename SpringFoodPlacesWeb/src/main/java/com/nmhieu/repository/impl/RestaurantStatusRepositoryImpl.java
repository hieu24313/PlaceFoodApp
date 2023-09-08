package com.nmhieu.repository.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nmhieu.repository.RestaurantStatusRepository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP
 */
@Repository
@Transactional
public class RestaurantStatusRepositoryImpl implements RestaurantStatusRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getRestaurantsStatus() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM RestaurantStatus WHERE active = true");
        return query.getResultList();
    }

}
