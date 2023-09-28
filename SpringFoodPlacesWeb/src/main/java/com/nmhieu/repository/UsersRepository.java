/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Users;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface UsersRepository {

    List<Users> getUsers(Map<String, String> params);
    int countUsers();
    boolean addOrUpdateUsers(Users user);
    int addUser_server(Users user);
    int updateUser_server(Users user);
    Users getUserById(int id);
    Users getUserByUsername(String username);
    boolean registerUser(Users user);
    boolean isUsernameExists(String username);
    boolean isEmailExists(String email);
    boolean isPhonenumberExists(String phonenumber);
    boolean deleteUsers(int id);
    Users getUserByUsername_new(String username);
//    boolean authUser(String username, String password);
    int authUser(String username, String password);
    Users addUser(Users user);
//    Users updateUser(Users user);
    int updateUser(Users user);
    Users getUserByPhonenumber(String phonenumber);
    Users getUserByEmail(String email);
    int changePassword(Users user);
    Users registerUserGoogle(Users user);
    int authUserLoginGoogle(String username, String password);
    boolean authPhoneNumber (String username);
//    Users getUserByPhoneNumber(String phoneNumber);
    
}
