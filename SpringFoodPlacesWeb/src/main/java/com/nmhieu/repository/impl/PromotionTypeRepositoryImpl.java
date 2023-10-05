/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.PromotionType;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.PromotionTypeRepository;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Repository
@Transactional
public class PromotionTypeRepositoryImpl implements PromotionTypeRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public PromotionType getPromotionTypeById(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PromotionType> criteriaQuery = builder.createQuery(PromotionType.class);
            Root<PromotionType> root = criteriaQuery.from(PromotionType.class);

            Predicate idPredicate = builder.equal(root.get("promotionTypeId"), id);

            Predicate otherCondition = builder.equal(root.get("active"), Boolean.TRUE);

            Predicate finalPredicate = builder.and(idPredicate, otherCondition);

            criteriaQuery.where(finalPredicate);
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
