/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.PromotionFooditems;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.repository.PromotionFoodItemsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Transactional
@Repository
public class PromotionFoodItemsRepositoryImpl implements PromotionFoodItemsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<PromotionFooditems> getPromotion_FoodItemByIdpromotion(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionFooditems> query = criteriaBuilder.createQuery(PromotionFooditems.class);
        Root rootPromo_Food = query.from(PromotionFooditems.class);

        query.select(rootPromo_Food);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String promotionId = params.get("promotionId");
            if (promotionId != null && !promotionId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootPromo_Food.get("promotionId"), Integer.parseInt(promotionId)));
            }

            String foodId = params.get("foodId");
            if (foodId != null && !foodId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootPromo_Food.get("foodId"), Integer.parseInt(foodId)));
            }

        }
        predicates.add(criteriaBuilder.equal(rootPromo_Food.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));
        Query final_query = session.createQuery(query);

        return final_query.getResultList();
    }

    @Override
    public PromotionFooditems getPromotion_FoodItemById(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PromotionFooditems> criteriaQuery = builder.createQuery(PromotionFooditems.class);
            Root<PromotionFooditems> root = criteriaQuery.from(PromotionFooditems.class);

            Predicate idPredicate = builder.equal(root.get("promotionFooditemsId"), id);

            Predicate otherCondition = builder.equal(root.get("active"), Boolean.TRUE);

            Predicate finalPredicate = builder.and(idPredicate, otherCondition);

            criteriaQuery.where(finalPredicate);
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deletePromotion_FoodItem(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            PromotionFooditems pro_Food = this.getPromotion_FoodItemById(id);
            pro_Food.setActive(Boolean.FALSE);
            session.update(pro_Food);
            return true;
        } catch (HibernateError e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
}
