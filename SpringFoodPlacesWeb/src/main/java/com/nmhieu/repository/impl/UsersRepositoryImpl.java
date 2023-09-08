/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository.impl;

import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.UsersRepository;
import com.nmhieu.service.RestaurantsService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HP
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment environment;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RestaurantsService restaurantsService;

    @Override
    public List<Users> getUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Users> query = criteriaBuilder.createQuery(Users.class);
        Root rootUsers = query.from(Users.class);

        query.select(rootUsers);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {

            String keyword = params.get("keyword");
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(rootUsers.get("firstname"), String.format("%%%s%%", keyword)),
                        criteriaBuilder.like(rootUsers.get("lastname"), String.format("%%%s%%", keyword)))
                );
            }

            String roleId = params.get("roleId");
            if (roleId != null && !roleId.isEmpty()) {
                // Chỗ này ảo ma :) không parse về Int là bugs ???
                // Mà á parse về Int thì IDE nó báo không cần thiết ???
                predicates.add(criteriaBuilder.equal(rootUsers.get("roleId"), Integer.parseInt(roleId)));
            }

            String userName = params.get("username");
            if (userName != null && !userName.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootUsers.get("username"), userName));
            }
        }
        predicates.add(criteriaBuilder.equal(rootUsers.get("active"), Boolean.TRUE));
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(criteriaBuilder.asc(rootUsers.get("userId")));
        Query final_query = session.createQuery(query);

        if (params != null) {
            String pageStr = params.get("page");
            if (pageStr != null && !pageStr.isEmpty()) {
                int pageInt = Integer.parseInt(pageStr);
                int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE_USERS"));

                final_query.setMaxResults(pageSize);
                final_query.setFirstResult((pageInt - 1) * pageSize);
            }
        }

        return final_query.getResultList();
    }

    @Override
    public int countUsers() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM Users WHERE active = TRUE");

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public boolean addOrUpdateUsers(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (user.getUserId() == null) {
                user.setActive(Boolean.TRUE);
                session.save(user);
                return true;
            } else {
                if (this.isPhonenumberExists(user.getPhonenumber())) {
                    return false;
                }

                if (this.isEmailExists(user.getEmail())) {
                    return false;
                }
                session.update(user);
                return true;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Users getUserById(int id) {

//        try {
//            Session session = this.factory.getObject().getCurrentSession();
//            return session.get(Users.class, id);
//        } catch (NoResultException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> root = criteriaQuery.from(Users.class);

            Predicate idPredicate = builder.equal(root.get("userId"), id);

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
    public Users getUserByUsername(String username) {

        try {
            Session session = this.factory.getObject().getCurrentSession();
            Query query = session.createQuery("FROM Users WHERE username=:username AND active = TRUE");
            query.setParameter("username", username);
            return (Users) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean registerUser(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            user.setActive(Boolean.TRUE);
            session.save(user);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE username = :username");
        query.setParameter("username", username);
        long count = (long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public boolean deleteUsers(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Users user = this.getUserById(id);
        try {
            if (user != null) {
                if (user.getActive().equals(Boolean.TRUE)) {
                    user.setActive(Boolean.FALSE);
                    session.update(user);
                    List<Restaurants> restaurant_list = this.restaurantsService.getRestaurantByUserId(id);
                    
                    if (!restaurant_list.isEmpty()) {
                        for (Restaurants restaurant : restaurant_list) {
                            this.restaurantsService.deleteRestaurants(restaurant.getRestaurantId());
                        }
                    }
                } else {
                    session.delete(user);
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
    public Users getUserByUsername_new(String username) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            Query query = session.createQuery("FROM Users WHERE username=:username AND active = TRUE");
            query.setParameter("username", username);
            return (Users) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public boolean authUser(String username, String password) {
//        Users user = this.getUserByUsername(username);
//        if (user != null) {
//            return this.passwordEncoder.matches(password, user.getPassword());
//        }
//        return false;
//    }
    //api register
    @Override
    public Users addUser(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            user.setActive(Boolean.TRUE);
            session.save(user);
            return user;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    @Override
//    public Users updateUser(Users user) {
//        Session session = this.factory.getObject().getCurrentSession();
//        try {
//            session.update(user);
//            return user;
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public int authUser(String username, String password) {
        Users user = this.getUserByUsername(username);
        if (user != null) {
            if (this.passwordEncoder.matches(password, user.getPassword()) == true) {
                return 1; // true
            } else {
                return 2; // sai mật khẩu
            }
        } else {
            return 3; // sai tài khoản
        }
    }

//    @Override
//    public int updateUser(Users user) {
//        Session session = this.factory.getObject().getCurrentSession();
//        Users user_db_phonenumber = this.getUserByPhonenumber(user.getPhonenumber());
//        Users user_db_email = this.getUserByEmail(user.getEmail());
//        try {
//            if (this.isPhonenumberExists(user.getPhonenumber()) == true) {
//                return 3; // số điện thoại đã được đăng ký
//            }
//
//            if (this.isEmailExists(user.getEmail()) == true) {
//                return 4; // email đã được đăng ký
//            }
//
//            session.update(user);
//            return 1;
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//            return 0;
//        }
//    }
    @Override
    public int updateUser(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(user);
            return 1;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE email = :email");
        query.setParameter("email", email);
        long count = (long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public boolean isPhonenumberExists(String phonenumber) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE phonenumber = :phonenumber");
        query.setParameter("phonenumber", phonenumber);
        long count = (long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public int addUser_server(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            user.setActive(Boolean.TRUE);
            session.save(user);
            return 1;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateUser_server(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(user);
            return 1;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Users getUserByPhonenumber(String phonenumber) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            Query query = session.createQuery("FROM Users WHERE phonenumber=:phonenumber AND active = TRUE");
            query.setParameter("phonenumber", phonenumber);
            return (Users) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Users getUserByEmail(String email) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            Query query = session.createQuery("FROM Users WHERE email=:email AND active = TRUE");
            query.setParameter("email", email);
            return (Users) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int changePassword(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(user);
            return 1;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
