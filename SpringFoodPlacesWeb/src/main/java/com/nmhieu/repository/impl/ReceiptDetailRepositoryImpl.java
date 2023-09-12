/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.repository.ReceiptDetailRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
@Transactional
@Repository
public class ReceiptDetailRepositoryImpl implements ReceiptDetailRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

    @Override
    public List<ReceiptDetail> getReceiptDetails(int receiptId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ReceiptDetail> query = criteriaBuilder.createQuery(ReceiptDetail.class);
            Root<ReceiptDetail> rootReceiptDetail = query.from(ReceiptDetail.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootReceiptDetail.get("receiptId"), receiptId));

            query.where(predicates.toArray(new Predicate[0]));

            query.orderBy(criteriaBuilder.desc(rootReceiptDetail.get("amount")));

            TypedQuery<ReceiptDetail> finalQuery = session.createQuery(query);

            return finalQuery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReceiptDetail> getReceiptDetailsByFoodId(int foodId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ReceiptDetail> query = criteriaBuilder.createQuery(ReceiptDetail.class);
            Root<ReceiptDetail> rootReceiptDetail = query.from(ReceiptDetail.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootReceiptDetail.get("fooditemId"), foodId));

            query.where(predicates.toArray(new Predicate[0]));

            TypedQuery<ReceiptDetail> finalQuery = session.createQuery(query);

            return finalQuery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

}
