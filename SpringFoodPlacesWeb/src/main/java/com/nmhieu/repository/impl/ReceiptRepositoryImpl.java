/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.mail.EmailService;
import com.nmhieu.pojo.Cart;
import com.nmhieu.pojo.Locationship;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.ReceiptStatus;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.FoodItemsRepository;
import com.nmhieu.repository.ReceiptRepository;
import com.nmhieu.repository.UsersRepository;
import com.sun.mail.util.MailConnectException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Transactional
@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private FoodItemsRepository foodItemsRepo;

    @Autowired
    private Environment env;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addReceipt(Map<String, Cart> carts) {
        Session session = this.factory.getObject().getCurrentSession();
        Receipts receipt = new Receipts();
        String list = "";
        String location = "";
        String phonenumber = "";
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Users user = this.userRepo.getUserByUsername_new(authentication.getName());

            receipt.setUserId(user);
            receipt.setReceiptDate(new Date());
            receipt.setActive(Boolean.TRUE);
            receipt.setStatusReceiptId(new ReceiptStatus(1));
            session.save(user);

            double totalAmount = 0;

            for (Cart cart : carts.values()) {
                list += " Tên sản phẩm: " + cart.getFoodName() + " Giá: " + cart.getUnitPrice() + " Số lượng: " + cart.getQuantity() + "<br />";
                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setFooditemId(this.foodItemsRepo.getFoodItemById(Integer.parseInt(cart.getFoodId().toString())));
                receiptDetail.setReceiptId(receipt);
                receiptDetail.setQuantity(cart.getQuantity());
                receiptDetail.setUnitPrice(BigDecimal.valueOf(cart.getUnitPrice()));
                double amount = cart.getQuantity() * cart.getUnitPrice();
                location = cart.getLocationuser();
                phonenumber = cart.getPhonenumberuser();
                System.out.println("a" + cart.getLocationuser());
                System.out.println("b" + cart.getPhonenumberuser());
                receiptDetail.setAmount(BigDecimal.valueOf(amount));
                totalAmount += amount;
                session.save(receiptDetail);
            }
            
            //luu dia chi giao hang
            Locationship ship = new Locationship();
            ship.setLocation(location);
            ship.setPhonenumber(phonenumber);
            ship.setReceiptId(receipt);
            session.save(ship);
            
            list += "Tổng: " + totalAmount;
            receipt.setTotalPayment(BigDecimal.valueOf(totalAmount));

            String subjectEmail = "Xin chào " + user.getFirstname() + " " + user.getLastname();
            String contentEmail = "Bạn vừa thanh toán hóa đơn: \n" + list;

            this.emailService.send_Email(user.getEmail(), contentEmail, subjectEmail);

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Receipts> getReceipts(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
//        Query query = session.createQuery("From Fooditems");
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Receipts> q = b.createQuery(Receipts.class);
        Root root = q.from(Receipts.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("userId");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.equal(root.get("userId"), Integer.parseInt(kw)));
            }
//
//            String fromPrice = params.get("fromPrice");
//            if (fromPrice != null && !fromPrice.isEmpty()) {
//                predicates.add(b.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice)));
//            }
//
//            String toPrice = params.get("toPrice");
//            if (toPrice != null && !toPrice.isEmpty()) {
//                predicates.add(b.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice)));
//            }
//
//            String cateFoodId = params.get("cateFoodId");
//            if (cateFoodId != null && !cateFoodId.isEmpty()) {
//                predicates.add(b.equal(root.get("categoryfoodId"), Integer.valueOf(cateFoodId)));
//            }
//
//            String restaurantId = params.get("restaurantId");
//            if (restaurantId != null && !restaurantId.isEmpty()) {
//                predicates.add(b.equal(root.get("restaurantId"), Integer.valueOf(restaurantId)));
//            }
//
            q.where(predicates.toArray(Predicate[]::new));

        }

        q.orderBy(b.desc(root.get("receiptDate")));
        Query query = session.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

                query.setMaxResults(pageSize);
                query.setFirstResult((p - 1) * pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public Receipts getReceiptById(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Receipts> criteriaQuery = builder.createQuery(Receipts.class);
            Root<Receipts> root = criteriaQuery.from(Receipts.class);

            Predicate idPredicate = builder.equal(root.get("receiptId"), id);

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
    public boolean updateAcceptReceipt(Receipts receipt) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(receipt);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addReceiptNoUser(Map<String, Cart> carts) {
        Session session = this.factory.getObject().getCurrentSession();
        Receipts receipt = new Receipts();
        try {
            String location = "";
            String phonenumber = "";
            receipt.setUserId(null);
            receipt.setReceiptDate(new Date());
            receipt.setActive(Boolean.TRUE);
            receipt.setStatusReceiptId(new ReceiptStatus(1));
            session.save(receipt);

            double totalAmount = 0;

            for (Cart cart : carts.values()) {
                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setFooditemId(this.foodItemsRepo.getFoodItemById(Integer.parseInt(cart.getFoodId().toString())));
                receiptDetail.setReceiptId(receipt);
                receiptDetail.setQuantity(cart.getQuantity());
                receiptDetail.setUnitPrice(BigDecimal.valueOf(cart.getUnitPrice()));
                double amount = cart.getQuantity() * cart.getUnitPrice();
                receiptDetail.setAmount(BigDecimal.valueOf(amount));
                location = cart.getLocationuser();
                phonenumber = cart.getPhonenumberuser();
                totalAmount += amount;
                session.save(receiptDetail);
            }
            receipt.setTotalPayment(BigDecimal.valueOf(totalAmount));
            //luu dia chi giao hang
            Locationship ship = new Locationship();
            ship.setLocation(location);
            ship.setPhonenumber(phonenumber);
            ship.setReceiptId(receipt);
            session.save(ship);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
