/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.Comments;
import com.nmhieu.repository.CommentsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
public class CommentsRepositoryImpl implements CommentsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

    @Override
    public List<Comments> getComments(int foodId, Map<String, String> params) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Comments> query = criteriaBuilder.createQuery(Comments.class);
            Root<Comments> rootComments = query.from(Comments.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootComments.get("foodId"), foodId));

            query.where(predicates.toArray(new Predicate[0]));

            query.orderBy(criteriaBuilder.desc(rootComments.get("createdDate")));

            TypedQuery<Comments> finalQuery = session.createQuery(query);

            if (params != null) {
                String pageStr = params.get("page");
                if (pageStr != null && !pageStr.isEmpty()) {
                    int pageInt = Integer.parseInt(pageStr);
                    int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE_COMMENTS"));

                    finalQuery.setMaxResults(pageSize);
                    finalQuery.setFirstResult((pageInt - 1) * pageSize);
                }
            }

            return finalQuery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Comments addComment(Comments comment) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            comment.setActive(Boolean.TRUE);
            session.save(comment);
            return comment;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public int countComments(int foodId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root rootComments = query.from(Comments.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(rootComments.get("foodId"), foodId));
        query.where(predicates.toArray(Predicate[]::new));

        query.select(criteriaBuilder.count(rootComments));
        return session.createQuery(query).getSingleResult().intValue();
    }

}
