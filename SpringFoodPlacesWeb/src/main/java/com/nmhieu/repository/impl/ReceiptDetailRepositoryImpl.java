/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.repository.ReceiptDetailRepository;
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

    @Override
    public List<Object> getNowReceiptByJoinFoodAndReceiptAndReceiptDetail(Map<String, String> params) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
//        Query query = session.createQuery("From Fooditems");
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Object> q = criteriaBuilder.createQuery(Object.class);
            Root rootFoodItem = q.from(Fooditems.class);
            Root rootReceipt = q.from(Receipts.class);
            Root rootReceiptDetail = q.from(ReceiptDetail.class);

            // SELECT MULTIPLE COLUMNS
            q.multiselect(
                    rootFoodItem,
                    rootReceipt,
                    rootReceiptDetail
            );

            // WHERE JOIN BẢNG
//            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.equal(
//                    rootFoodItem.get("foodId"),
//                    rootReceiptDetail.get("fooditemId"))
//            );
//            String restaurantId = params.get("restaurantId");
//            if (restaurantId != null) {
//                predicates.add(criteriaBuilder.equal(rootFoodItem.get("restaurantId"),
//                        Integer.valueOf(params.get("restaurantId")))
//                );
//            }
//
//            predicates.add(criteriaBuilder.equal(
//                    rootReceipt.get("receiptId"),
//                    rootReceiptDetail.get("receiptId"))
//            );
//            q.where(predicates.toArray(Predicate[]::new));
            q.where(
                    criteriaBuilder.equal(rootFoodItem.get("foodId"), rootReceiptDetail.get("fooditemId")),
                    criteriaBuilder.equal(rootFoodItem.get("restaurantId"), 20),
                    criteriaBuilder.equal(rootReceipt.get("receiptId"), rootReceiptDetail.get("receiptId"))
            );

            Query final_query = session.createQuery(q);
            return final_query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

}
