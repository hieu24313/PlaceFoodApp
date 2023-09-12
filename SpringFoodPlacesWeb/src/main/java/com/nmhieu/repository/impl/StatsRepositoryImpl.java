/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.repository.StatsRepository;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author HP
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private SimpleDateFormat MY_DATE_FORMAT;

//    @Autowired
//    private CustomDateEditor MY_CustomDateEditor;
//    
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, MY_CustomDateEditor);
//    }
    @Override
    public List<Object[]> statsRevenue(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);

        Root rootFoodItems = query.from(Fooditems.class);
        Root rootReceipts = query.from(Receipts.class);
        Root rootReceiptDetail = query.from(ReceiptDetail.class);
//        Root rootCategory = query.from(CategoriesFood.class);

//        Cái này khai báo ra dù không xài nó cũng tự join dô luôn :) U là trời :) làm tốn hết tiếng đồng hồ
//        Root rootRestaurants = query.from(Restaurants.class);
        // SELECT 
        query.multiselect(
                rootFoodItems.get("foodId"),
                rootFoodItems.get("foodName"),
                rootReceiptDetail.get("amount"),
                criteriaBuilder.sum(rootReceiptDetail.get("quantity"))
        //                rootCategory.get("categoryfoodId"),
        //                rootCategory.get("categoryname")
        //                        criteriaBuilder.sum(
        //                                criteriaBuilder.prod(
        //                                        rootReceiptDetail.get("unitPrice"),
        //                                        rootReceiptDetail.get("quantity"))
        //                        )
        );

        List<Predicate> predicates = new ArrayList<>();

        // WHERE JOIN BẢNG
        predicates.add(criteriaBuilder.equal(
                rootReceipts.get("receiptId"),
                rootReceiptDetail.get("receiptId"))
        );

        predicates.add(criteriaBuilder.equal(
                rootReceiptDetail.get("fooditemId"),
                rootFoodItems.get("foodId"))
        );

//        predicates.add(criteriaBuilder.equal(
//                rootCategory.get("categoryfoodId"),
//                rootFoodItems.get("categoryfoodId"))
//        );
        // LỌC
        String restaurantId = params.get("restaurantId");

        if (restaurantId != null && !restaurantId.isEmpty()) {
            predicates.add(criteriaBuilder.equal(rootFoodItems.get("restaurantId"), Integer.parseInt(restaurantId)));

        }

        String fromDate = params.get("fromDate");
        if (fromDate != null && !fromDate.isEmpty()) {
            try {
//                String taoLao = "2023-08-08 00:00:00";
//                Timestamp fromDateStamp = new Timestamp(MY_DATE_FORMAT.parse(fromDate).getTime());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(fromDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String toDate = params.get("toDate");
        if (toDate != null && !toDate.isEmpty()) {
            try {
//                String taoLao = "2023-08-11 00:00:00";
//                Timestamp toDateStamp = new Timestamp(MY_DATE_FORMAT.parse(toDate).getTime());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(toDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("quarter-year");
            if (year != null && !year.isEmpty()) {
                predicates.addAll(Arrays.asList(
                        criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(year)),
                        criteriaBuilder.equal(criteriaBuilder.function("QUARTER", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(quarter))
                ));
            }
        }

        // Phần sau WHERE
        query.orderBy(criteriaBuilder.desc(rootReceiptDetail.get("amount")));
        query.where(predicates.toArray(Predicate[]::new));

        query.groupBy(rootFoodItems.get("foodId"),
                rootFoodItems.get("foodName"),
                rootReceiptDetail.get("amount"));
        Query final_query = session.createQuery(query);
        return final_query.getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByCate(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);

        Root rootFoodItems = query.from(Fooditems.class);
        Root rootReceipts = query.from(Receipts.class);
        Root rootReceiptDetail = query.from(ReceiptDetail.class);
        Root rootCategory = query.from(CategoriesFood.class);

//        Cái này khai báo ra dù không xài nó cũng tự join dô luôn :) U là trời :) làm tốn hết tiếng đồng hồ
//        Root rootRestaurants = query.from(Restaurants.class);
        // SELECT 
        query.multiselect(
                rootCategory.get("categoryfoodId"),
                rootCategory.get("categoryname"),
                criteriaBuilder.sum(rootReceiptDetail.get("amount"))
        );

        List<Predicate> predicates = new ArrayList<>();

        // WHERE JOIN BẢNG
        predicates.add(criteriaBuilder.equal(
                rootReceipts.get("receiptId"),
                rootReceiptDetail.get("receiptId"))
        );

        predicates.add(criteriaBuilder.equal(
                rootReceiptDetail.get("fooditemId"),
                rootFoodItems.get("foodId"))
        );

        predicates.add(criteriaBuilder.equal(
                rootCategory.get("categoryfoodId"),
                rootFoodItems.get("categoryfoodId"))
        );
        // LỌC
        String restaurantId = params.get("restaurantId");

        if (restaurantId != null && !restaurantId.isEmpty()) {
            predicates.add(criteriaBuilder.equal(rootFoodItems.get("restaurantId"), Integer.parseInt(restaurantId)));

        }

        String fromDate = params.get("fromDate");
        if (fromDate != null && !fromDate.isEmpty()) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(fromDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String toDate = params.get("toDate");
        if (toDate != null && !toDate.isEmpty()) {
            try {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(toDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("quarter-year");
            if (year != null && !year.isEmpty()) {
                predicates.addAll(Arrays.asList(
                        criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(year)),
                        criteriaBuilder.equal(criteriaBuilder.function("QUARTER", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(quarter))
                ));
            }
        }

        // Phần sau WHERE
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(rootReceiptDetail.get("amount"))));
        query.where(predicates.toArray(Predicate[]::new));

        query.groupBy(
                rootCategory.get("categoryfoodId")
        );
        Query final_query = session.createQuery(query);
        return final_query.getResultList();
    }

    @Override
    public List<Object[]> statsRestaurant(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);

        Root rootFoodItems = query.from(Fooditems.class);
        Root rootReceipts = query.from(Receipts.class);
        Root rootReceiptDetail = query.from(ReceiptDetail.class);

        Root rootRestaurants = query.from(Restaurants.class);
        // SELECT 
        query.multiselect(
                rootRestaurants.get("restaurantId"),
                rootRestaurants.get("restaurantName"),
                criteriaBuilder.sum(rootReceiptDetail.get("amount"))
        );

        List<Predicate> predicates = new ArrayList<>();

        // WHERE JOIN BẢNG
        predicates.add(criteriaBuilder.equal(
                rootReceipts.get("receiptId"),
                rootReceiptDetail.get("receiptId"))
        );

        predicates.add(criteriaBuilder.equal(
                rootReceiptDetail.get("fooditemId"),
                rootFoodItems.get("foodId"))
        );

        predicates.add(criteriaBuilder.equal(
                rootRestaurants.get("restaurantId"),
                rootFoodItems.get("restaurantId"))
        );
        // LỌC
        String restaurantId = params.get("restaurantId");

        if (restaurantId != null && !restaurantId.isEmpty()) {
            predicates.add(criteriaBuilder.equal(rootFoodItems.get("restaurantId"), Integer.parseInt(restaurantId)));

        }

        String fromDate = params.get("fromDate");
        if (fromDate != null && !fromDate.isEmpty()) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(fromDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String toDate = params.get("toDate");
        if (toDate != null && !toDate.isEmpty()) {
            try {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(rootReceipts.get("receiptDate"), MY_DATE_FORMAT.parse(toDate)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("quarter-year");
            if (year != null && !year.isEmpty()) {
                predicates.addAll(Arrays.asList(
                        criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(year)),
                        criteriaBuilder.equal(criteriaBuilder.function("QUARTER", Integer.class, rootReceipts.get("receiptDate")), Integer.parseInt(quarter))
                ));
            }
        }

        // Phần sau WHERE
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(rootReceiptDetail.get("amount"))));
        query.where(predicates.toArray(Predicate[]::new));

        query.groupBy(
                rootRestaurants.get("restaurantId"),
                rootRestaurants.get("restaurantName")
        );
        Query final_query = session.createQuery(query);
        return final_query.getResultList();
    }
}
