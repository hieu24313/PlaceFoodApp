/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.RestaurantStatus;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Roles;
import com.nmhieu.pojo.ShelfLife;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.RestaurantsRepository;
import com.nmhieu.repository.UsersRepository;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.ShelfLifeService;
import com.nmhieu.service.UsersService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class RestaurantsRepositoryImpl implements RestaurantsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

//    @Autowired
//    private UsersRepository userRepo;
    @Autowired
    private UsersService usersService;

    @Autowired
    private CategoriesFoodService categoriesFoodService;

    @Autowired
    private ShelfLifeService shelfLifeService;

    @Override
    public List<Restaurants> getRestaurants(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Restaurants> query = criteriaBuilder.createQuery(Restaurants.class);
        Root rootRestaurants = query.from(Restaurants.class);

        query.select(rootRestaurants);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String confirm = params.get("confirm");
            if (confirm != null && !confirm.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootRestaurants.get("confirmationStatus"), Boolean.parseBoolean(confirm)));
            }

            String userId = params.get("current_user_UserId");
            if (userId != null && !userId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootRestaurants.get("userId"), Integer.parseInt(userId)));
            }

            String location = params.get("location");
            if (location != null && !location.isEmpty()) {
                predicates.add(criteriaBuilder.like(rootRestaurants.get("location"), String.format("%%%s%%", location)));
            }

            String restaurantName = params.get("restaurantName");
            if (restaurantName != null && !restaurantName.isEmpty()) {
                predicates.add(criteriaBuilder.like(rootRestaurants.get("restaurantName"), String.format("%%%s%%", restaurantName)));
            }

        }
        predicates.add(criteriaBuilder.equal(rootRestaurants.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(criteriaBuilder.asc(rootRestaurants.get("restaurantId")));
        Query final_query = session.createQuery(query);

        if (params != null) {
            String pageStr = params.get("page");
            if (pageStr != null && !pageStr.isEmpty()) {
                int pageInt = Integer.parseInt(pageStr);
                int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE"));

                final_query.setMaxResults(pageSize);
                final_query.setFirstResult((pageInt - 1) * pageSize);
            }
        }
        return final_query.getResultList();
    }

    @Override
    public boolean addOrUpdateRestaurants(Restaurants restaurant) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (restaurant.getRestaurantId() == null) {
                restaurant.setActive(Boolean.TRUE);
                session.save(restaurant);
            } else {
                Users user = this.usersService.getUserById(restaurant.getUserId().getUserId());

                if (user != null) {
                    if (user.getRoleId().getRoleId() == 3) {
                        user.setRoleId(new Roles(2));
                        session.update(user);
                    }
                    session.update(restaurant);
                } else {
                    return false;
                }
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Restaurants getRestaurantById(int id) {
//        try {
//            Session session = this.factory.getObject().getCurrentSession();
//            return session.get(Restaurants.class, id);
//        } catch (NoResultException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Restaurants> criteriaQuery = builder.createQuery(Restaurants.class);
            Root<Restaurants> root = criteriaQuery.from(Restaurants.class);

            Predicate idPredicate = builder.equal(root.get("restaurantId"), id);

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
    public boolean deleteRestaurants(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Restaurants restaurants = this.getRestaurantById(id);
        try {
            if (restaurants != null) {
                if (restaurants.getActive().equals(Boolean.TRUE)) {
                    restaurants.setActive(Boolean.FALSE);
                    session.update(restaurants);
                    List<CategoriesFood> category_list = this.categoriesFoodService.getCategoriesFoodByRestaurantId(id);

                    if (!category_list.isEmpty()) {
                        for (CategoriesFood cate : category_list) {
                            this.categoriesFoodService.delCategory(cate.getCategoryfoodId());
                        }
                    }

                    List<ShelfLife> shelflife_list = this.shelfLifeService.getShelfLifeByRestaurantId(id);

                    if (!shelflife_list.isEmpty()) {
                        for (ShelfLife sl : shelflife_list) {
                            this.shelfLifeService.delShelf(sl.getShelflifeId());
                        }
                    }

                } else {
                    session.delete(restaurants);
                }
            } else {
                return false;
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    @Override
//    public List<Object[]> getRestaurantsNotConfirm(Map<String, String> params) {
//        Session session = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Restaurants> query = criteriaBuilder.createQuery(Restaurants.class);
//        Root rootRestaurants = query.from(Restaurants.class);
//
//        query.select(rootRestaurants);
//        
//        if (params != null) {
//            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.equal(rootRestaurants.get("confirmationStatus"), false));
//            
//            
//             query.where(predicates.toArray(Predicate[]::new));
//        }
//
//        query.orderBy(criteriaBuilder.asc(rootRestaurants.get("restaurantId")));
//        Query final_query = session.createQuery(query);
//
//        return final_query.getResultList();
//    }
    @Override
    public int countRestaurants(Map<String, String> params) {
//        Session session = this.factory.getObject().getCurrentSession();
//        Query query = session.createQuery("SELECT Count(*) FROM Restaurants");

        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root rootRestaurants = query.from(Restaurants.class);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String confirm = params.get("confirm");

            if (confirm != null && !confirm.isEmpty()) {
                if (confirm.equals("true")) {
                    predicates.add(criteriaBuilder.equal(rootRestaurants.get("confirmationStatus"), Boolean.parseBoolean("true")));
                } else {
                    predicates.add(criteriaBuilder.equal(rootRestaurants.get("confirmationStatus"), Boolean.parseBoolean("false")));
                }
            }

            String userId = params.get("current_user_UserId");

            if (userId != null && !userId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootRestaurants.get("userId"), Integer.parseInt(userId)));
            }

        }
        predicates.add(criteriaBuilder.equal(rootRestaurants.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));
        query.select(criteriaBuilder.count(rootRestaurants));

        return session.createQuery(query).getSingleResult().intValue();
    }

    @Override
    public List<Restaurants> getRestaurantByUserId(int userId) {
//        try {
//            Session session = this.factory.getObject().getCurrentSession();
//            Query query = session.createQuery("FROM Restaurants WHERE userId=:userId AND active = true");
//            query.setParameter("userId", userId);
//            return query.getResultList();
//        } catch (NoResultException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Restaurants> criteriaQuery = builder.createQuery(Restaurants.class);
            Root<Restaurants> root = criteriaQuery.from(Restaurants.class);

            Predicate idPredicate = builder.equal(root.get("userId"), userId);

            Predicate otherCondition = builder.equal(root.get("active"), Boolean.TRUE);

            Predicate finalPredicate = builder.and(idPredicate, otherCondition);

            criteriaQuery.where(finalPredicate);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Restaurants registerRestaurant(Restaurants restaurant) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users user = this.usersService.getUserByUsername_new(authentication.getName());

            restaurant.setUserId(user);
            restaurant.setActive(Boolean.TRUE);
            session.save(restaurant);
            return restaurant;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkUserAndRestaurant(Map<String, String> params) {
        int restaurantId = Integer.parseInt(params.get("restaurantId"));
        int userId = Integer.parseInt(params.get("userId"));
        Restaurants restaurant = this.getRestaurantById(restaurantId);
        System.out.println(restaurantId);
        System.out.println(restaurant);
        if(restaurant == null){
            return false;
        }
        else{
            return restaurant.getUserId().getUserId() == userId;
        }
    }
    
    

}
