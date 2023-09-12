/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.Follow;
import com.nmhieu.repository.FollowRepository;
import com.nmhieu.service.FollowService;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FollowRepositoryImpl implements FollowRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

    @Autowired
    private FollowService followService;

    @Override
    public int addFollow(Follow follow) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            int userId = follow.getUserIdIndex().getUserId();
            int restaurantId = follow.getRestaurantIdIndex().getRestaurantId();
            Follow follow_db = this.followService.getFollowByUserIdAndRestaurantId(userId, restaurantId);

            // Đã follow rồi
            if (follow_db != null) {
                session.delete(follow_db);
                return 2; // Đã hủy follow
            } else {
                session.save(follow);
                return 1; // Follow thành công
            }

        } catch (HibernateException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Follow getFollowByUserIdAndRestaurantId(int userId, int restaurantId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Follow> criteriaQuery = builder.createQuery(Follow.class);
            Root<Follow> root = criteriaQuery.from(Follow.class);

            // :)))))
            Predicate idPredicate = builder.equal(root.get("restaurantIdIndex"), restaurantId);

            Predicate idPredicate_1 = builder.equal(root.get("userIdIndex"), userId);

            Predicate finalPredicate = builder.and(idPredicate, idPredicate_1);

            criteriaQuery.where(finalPredicate);
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Follow checkFollow(Follow follow) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            session.update(follow);
            return follow;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Follow> getFollowByRestaurantId(int restaurantId) {
       try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Follow> criteriaQuery = builder.createQuery(Follow.class);
            Root<Follow> root = criteriaQuery.from(Follow.class);

            Predicate idPredicate = builder.equal(root.get("restaurantIdIndex"), restaurantId);


            Predicate finalPredicate = builder.and(idPredicate);

            criteriaQuery.where(finalPredicate);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
