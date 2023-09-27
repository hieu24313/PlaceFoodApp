/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.Users;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Service
public interface UsersService extends UserDetailsService {

    List<Users> getUsers(Map<String, String> params);

    int countUsers();

    boolean addOrUpdateUsers(Users user);

    int addUser_server(Users user);

    int updateUser_server(Users user);

    Users getUserById(int id);

    boolean registerUser(Users user);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);

    boolean isPhonenumberExists(String phonenumber);

    boolean deleteUsers(int id);

    Users getUserByUsername_new(String username);

//    boolean authUser(String username, String password);
    int authUser(String username, String password);

    Users addUser(Map<String, String> params, MultipartFile avatar);

//    Users updateUser(Map<String, String> params, MultipartFile avatar);
    int updateUser(Map<String, String> params, MultipartFile avatar);

    Users getUserByPhonenumber(String phonenumber);

    Users getUserByEmail(String email);

    int changePassword(Map<String, String> params);

    Users registerUserGoogle(Map<String, String> params, MultipartFile avatar);

    int authUserLoginGoogle(String username, String password);
    
    boolean authPhoneNumber(String username);

}
