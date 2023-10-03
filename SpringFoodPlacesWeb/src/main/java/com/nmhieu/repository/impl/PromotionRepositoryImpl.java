/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Promotion;
import com.nmhieu.pojo.PromotionFooditems;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.PromotionRepository;
import com.nmhieu.service.FoodItemsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
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
public class PromotionRepositoryImpl implements PromotionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private FoodItemsService foodService;
    @Autowired
    private SimpleDateFormat MY_DATE_FORMAT;

    @Override
    public List<Promotion> getPromotion(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Promotion> query = criteriaBuilder.createQuery(Promotion.class);
        Root RootPromotion = query.from(Promotion.class);

        query.select(RootPromotion);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String keyword = params.get("promotionName");
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(RootPromotion.get("promotionName"), String.format("%%%s%%", keyword))
                );
            }

            String restaurantId = params.get("restaurantId");
            if (restaurantId != null && !restaurantId.isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(RootPromotion.get("restaurantId"), Integer.valueOf(restaurantId))
                );
            }
            
            String fromDate = params.get("fromDate");
        if (fromDate != null && !fromDate.isEmpty()) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(RootPromotion.get("receiptDate"), MY_DATE_FORMAT.parse(fromDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String toDate = params.get("toDate");
        if (toDate != null && !toDate.isEmpty()) {
            try {
//                String taoLao = "2023-08-11 00:00:00";
//                Timestamp toDateStamp = new Timestamp(MY_DATE_FORMAT.parse(toDate).getTime());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(RootPromotion.get("receiptDate"), MY_DATE_FORMAT.parse(toDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//            query.where(predicates.toArray(Predicate[]::new));
        }

        predicates.add(criteriaBuilder.equal(RootPromotion.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));

        Query final_query = session.createQuery(query);
        return final_query.getResultList();
    }

    @Override
    public Promotion getPromotionById(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Promotion> criteriaQuery = builder.createQuery(Promotion.class);
            Root<Promotion> root = criteriaQuery.from(Promotion.class);

            Predicate idPredicate = builder.equal(root.get("promotionId"), id);

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
    public boolean addPromotionForFood(int idFood, int idPromotion) {
//        Session session = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<PromotionFooditems> query = criteriaBuilder.createQuery(PromotionFooditems.class);
//        Root rootRestaurants = query.from(PromotionFooditems.class);
        Session session = this.factory.getObject().getCurrentSession();
        PromotionFooditems pro_Food = new PromotionFooditems();
        pro_Food.setFoodId(this.foodService.getFoodItemById(idFood));
        pro_Food.setPromotionId(this.getPromotionById(idPromotion));
        pro_Food.setActive(Boolean.TRUE);
        try {
            session.save(pro_Food);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addOrUpdatePromotion(Promotion promotion) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (promotion.getPromotionId() == null) {
                promotion.setActive(Boolean.TRUE);
                session.save(promotion);
                return true;
            } else{
                session.update(promotion);
                return true;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
