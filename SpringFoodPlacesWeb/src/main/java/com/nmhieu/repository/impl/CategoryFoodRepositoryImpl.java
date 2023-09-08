/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.repository.CategoryFoodRepository;
import com.nmhieu.service.FoodItemsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class CategoryFoodRepositoryImpl implements CategoryFoodRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

    @Autowired
    private FoodItemsService foodItemsService;

//    @Override
//    public List<Object[]> getCategoriesFood(Map<String, String> params) {
//        Session session = this.factory.getObject().getCurrentSession();
//        Query query = session.createQuery("FROM CategoriesFood");
//        return query.getResultList();
//    }
    @Override
    public List<CategoriesFood> getCategoriesFood(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CategoriesFood> query = criteriaBuilder.createQuery(CategoriesFood.class);
        Root rootCate = query.from(CategoriesFood.class);

        query.select(rootCate);

        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String keyword = params.get("keyword");
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(rootCate.get("categoryname"), String.format("%%%s%%", keyword))
                );
            }

            String restaurantId = params.get("restaurantId");
            if (restaurantId != null && !restaurantId.isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(rootCate.get("restaurantId"), Integer.valueOf(restaurantId))
                );
            }
//            query.where(predicates.toArray(Predicate[]::new));
        }

        predicates.add(criteriaBuilder.equal(rootCate.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));

        Query final_query = session.createQuery(query);
        return final_query.getResultList();
    }

    @Override
    public int countCategoriesFood() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM CategoriesFood WHERE active = TRUE");

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public boolean addOrUpdateCate(CategoriesFood cate) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (cate.getCategoryfoodId() == null) {
                cate.setActive(Boolean.TRUE);
                session.save(cate);
            } else {
                session.update(cate);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public CategoriesFood getCategoryById(int id) {
//        try {
//            Session session = this.factory.getObject().getCurrentSession();
//            return session.get(CategoriesFood.class, id);
//        } catch (NoResultException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CategoriesFood> criteriaQuery = builder.createQuery(CategoriesFood.class);
            Root<CategoriesFood> root = criteriaQuery.from(CategoriesFood.class);

            Predicate idPredicate = builder.equal(root.get("categoryfoodId"), id);

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
    public boolean delCategory(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CategoriesFood cate = this.getCategoryById(id);
        try {
            if (cate != null) {
                if (cate.getActive().equals(Boolean.TRUE)) {
                    cate.setActive(Boolean.FALSE);
                    session.update(cate);
                    List<Fooditems> foodItem_list = this.foodItemsService.getFoodItemsByCategoryId(id);

                    for (Fooditems food : foodItem_list) {
                        this.foodItemsService.delFoodItem(food.getFoodId());
                    }

                } else {
                    session.delete(cate);
                }
            }
            else {
                return false;
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CategoriesFood> getCategoriesFoodByRestaurantId(int restaurantId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CategoriesFood> query = criteriaBuilder.createQuery(CategoriesFood.class);
            Root rootCate = query.from(CategoriesFood.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootCate.get("restaurantId"), restaurantId));
            predicates.add(criteriaBuilder.equal(rootCate.get("active"), Boolean.TRUE));
            query.where(predicates.toArray(Predicate[]::new));

            return session.createQuery(query).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

}
