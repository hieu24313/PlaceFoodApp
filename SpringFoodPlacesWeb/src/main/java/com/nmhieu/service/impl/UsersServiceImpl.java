/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nmhieu.pojo.Roles;
import com.nmhieu.pojo.Users;
import com.nmhieu.repository.RolesRepository;
import com.nmhieu.repository.UsersRepository;
import com.nmhieu.service.UsersService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Service("userDetailsService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Cloudinary cloudinary;

//    @Override
//    public List<Object[]> getUsers() {
//        return this.usersRepo.getUsers();
//    }
    @Override
    public List<Users> getUsers(Map<String, String> params) {
        return this.usersRepo.getUsers(params);
    }

    @Override
    public int countUsers() {
        return this.usersRepo.countUsers();
    }

    @Override
    public boolean addOrUpdateUsers(Users user) {
        if (!user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(RestaurantsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        boolean isUsernameExists = this.usersRepo.isUsernameExists(user.getUsername());
        boolean isPhonenumberExists = this.usersRepo.isPhonenumberExists(user.getPhonenumber());
        boolean isEmailExists = this.usersRepo.isEmailExists(user.getEmail());

        if (isUsernameExists == true) {
            return this.usersRepo.addOrUpdateUsers(user);
        } else {
            String password = user.getPassword();
            user.setPassword(this.bCryptPasswordEncoder.encode(password));
            return this.usersRepo.addOrUpdateUsers(user);
        }
    }

    @Override
    public Users getUserById(int id) {
        return this.usersRepo.getUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.usersRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("LỖI XÁC THỰC NHA");
        } else {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(user.getRoleId().getRoleName()));
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), authorities);
        }

    }

    @Override
    public boolean registerUser(Users user) {

        boolean isUsernameExists = this.usersRepo.isUsernameExists(user.getUsername());

        if (isUsernameExists == true) {
            return false;
        } else {
            String password = user.getPassword();
            user.setPassword(this.bCryptPasswordEncoder.encode(password));
            user.setRoleId(new Roles(3));
            return this.usersRepo.registerUser(user);
        }

//        try {
//            
//            return false;
//
//        } catch (UsernameNotFoundException ex) {
//            String password = user.getPassword();
//            user.setPassword(this.bCryptPasswordEncoder.encode(password));
//            user.setRoleId(new Roles(3));
//            return this.usersRepo.registerUser(user);
//        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        return this.usersRepo.isUsernameExists(username);
    }

    @Override
    public boolean deleteUsers(int id) {
        return this.usersRepo.deleteUsers(id);
    }

    @Override
    public Users getUserByUsername_new(String username) {
        return this.usersRepo.getUserByUsername_new(username);
    }

//    @Override
//    public boolean authUser(String username, String password) {
//        return this.usersRepo.authUser(username, password);
//    }
    //api register
    @Override
    public Users addUser(Map<String, String> params, MultipartFile avatar) {
        boolean isUsernameExists = this.usersRepo.isUsernameExists(params.get("username"));

        if (isUsernameExists == true) {
            return null;
        } else {
            Users user = new Users();
            user.setFirstname(params.get("firstname"));
            user.setLastname(params.get("lastname"));
            user.setPhonenumber(params.get("phonenumber"));
            user.setLocation(params.get("location"));
            user.setEmail(params.get("email"));
            user.setUsername(params.get("username"));
            user.setPassword(this.bCryptPasswordEncoder.encode(params.get("password")));
            user.setRoleId(new Roles(3));
            if (!avatar.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.usersRepo.addUser(user);
            return user;
        }
    }

//    @Override
//    public Users updateUser(Map<String, String> params, MultipartFile avatar) {
//        String username = params.get("username");
//        boolean isUsernameExists = this.usersRepo.isUsernameExists(username);
//
//        if (isUsernameExists != true) {
//            return null;
//        } else {
//            Users user = this.getUserByUsername_new(username);
//
//            String firstname = params.get("firstname");
//            String lastname = params.get("lastname");
//            String phonenumber = params.get("phonenumber");
//            String location = params.get("location");
//            String email = params.get("email");
//
//            if (firstname != null && !firstname.isEmpty()) {
//                user.setFirstname(params.get("firstname"));
//            }
//
//            if (lastname != null && !lastname.isEmpty()) {
//                user.setLastname(params.get("lastname"));
//            }
//
//            if (phonenumber != null && !phonenumber.isEmpty()) {
//                user.setPhonenumber(params.get("phonenumber"));
//            }
//
//            if (location != null && !location.isEmpty()) {
//                user.setLocation(params.get("location"));
//            }
//
//            if (email != null && !email.isEmpty()) {
//                user.setEmail(params.get("email"));
//            }
//
//            if (!avatar.isEmpty()) {
//                try {
//                    Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
//                            ObjectUtils.asMap("resource_type", "auto"));
//                    user.setAvatar(res.get("secure_url").toString());
//                } catch (IOException ex) {
//                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            this.usersRepo.updateUser(user);
//            return user;
//        }
//    }
//    @Override
//    public int updateUser(Map<String, String> params, MultipartFile avatar) {
//        String username = params.get("username");
//        boolean isUsernameExists = this.usersRepo.isUsernameExists(username);
//
//        if (isUsernameExists != true) {
//            return 2; // Không tìm thấy username để update
//        } else {
//            Users user = this.getUserByUsername_new(username);
//
//            String firstname = params.get("firstname");
//            String lastname = params.get("lastname");
//            String phonenumber = params.get("phonenumber");
//            String location = params.get("location");
//            String email = params.get("email");
//
//            if (firstname != null && !firstname.isEmpty()) {
//                user.setFirstname(params.get("firstname"));
//            }
//
//            if (lastname != null && !lastname.isEmpty()) {
//                user.setLastname(params.get("lastname"));
//            }
//
//            if (phonenumber != null && !phonenumber.isEmpty()) {
//                user.setPhonenumber(params.get("phonenumber"));
//            }
//
//            if (location != null && !location.isEmpty()) {
//                user.setLocation(params.get("location"));
//            }
//
//            if (email != null && !email.isEmpty()) {
//                user.setEmail(params.get("email"));
//            }
//
//            if (!avatar.isEmpty()) {
//                try {
//                    Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
//                            ObjectUtils.asMap("resource_type", "auto"));
//                    user.setAvatar(res.get("secure_url").toString());
//                } catch (IOException ex) {
//                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            return this.usersRepo.updateUser(user);
//        }
//    }
    @Override
    public int updateUser(Map<String, String> params, MultipartFile avatar) {
        String username = params.get("username");
        boolean isUsernameExists = this.usersRepo.isUsernameExists(username);

        if (isUsernameExists != true) {
            return 2; // Không tìm thấy username để update
        } else {
            Users user = this.getUserByUsername_new(username);

            String firstname = params.get("firstname");
            String lastname = params.get("lastname");
            String phonenumber = params.get("phonenumber");
            String location = params.get("location");
            String email = params.get("email");

            if (firstname != null && !firstname.isEmpty()) {
                user.setFirstname(params.get("firstname"));
            }

            if (lastname != null && !lastname.isEmpty()) {
                user.setLastname(params.get("lastname"));
            }

            if (phonenumber != null && !phonenumber.isEmpty()) {
                Users user_db_phonenumber = this.usersRepo.getUserByPhonenumber(phonenumber);
                if (user_db_phonenumber != null) {
                    if (this.usersRepo.isPhonenumberExists(user.getPhonenumber()) == true && user_db_phonenumber.getUserId() != user.getUserId()) {
                        return 3; // số điện thoại đã được đăng ký
                    }

                }
                user.setPhonenumber(params.get("phonenumber"));
            }

            if (location != null && !location.isEmpty()) {
                user.setLocation(params.get("location"));
            }

            if (email != null && !email.isEmpty()) {
                Users user_db_email = this.usersRepo.getUserByEmail(email);
                if (user_db_email != null) {
                    if (this.usersRepo.isEmailExists(user.getEmail()) == true && user_db_email.getUserId() != user.getUserId()) {
                        return 4; // email đã được đăng ký
                    }

                }
                user.setEmail(params.get("email"));
            }

            if (!avatar.isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return this.usersRepo.updateUser(user);
        }
    }

    @Override
    public int authUser(String username, String password) {
        return this.usersRepo.authUser(username, password);
    }

    @Override
    public boolean isEmailExists(String email) {
        return this.usersRepo.isEmailExists(email);
    }

    @Override
    public boolean isPhonenumberExists(String phonenumber) {
        return this.usersRepo.isPhonenumberExists(phonenumber);
    }

    @Override
    public int addUser_server(Users user) {
        if (this.isPhonenumberExists(user.getPhonenumber())) {
            return 2; // số điện thoại đã được đăng ký
        }

        if (this.isEmailExists(user.getEmail())) {
            return 3; // email đã được đăng ký
        }
        if (this.usersRepo.isUsernameExists(user.getUsername()) == true) {
            return 4; // Tài khoản đã được đăng ký
        } else {
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoleId(new Roles(3));
            if (!user.getFile().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return this.usersRepo.addUser_server(user);
        }
    }

    @Override
    public int updateUser_server(Users user) {
        Users user_db_phonenumber = this.usersRepo.getUserByPhonenumber(user.getPhonenumber());
        Users user_db_email = this.usersRepo.getUserByEmail(user.getEmail());

        if (this.isPhonenumberExists(user.getPhonenumber()) == true && user_db_phonenumber.getUserId() != user.getUserId()) {
            return 2; // số điện thoại đã được đăng ký
        }

        if (this.isEmailExists(user.getEmail()) == true && user_db_email.getUserId() != user.getUserId()) {
            return 3; // email đã được đăng ký
        }

        if (this.usersRepo.isUsernameExists(user.getUsername()) != true) {
            return 4; // Không tìm thấy username để update
        } else {

            if (!user.getFile().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(UsersServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return this.usersRepo.updateUser_server(user);
        }
    }

    @Override
    public Users getUserByPhonenumber(String phonenumber) {
        return this.usersRepo.getUserByPhonenumber(phonenumber);
    }

    @Override
    public Users getUserByEmail(String email) {
        return this.usersRepo.getUserByEmail(email);
    }

    @Override
    public int changePassword(Map<String, String> params) {
        //confirmPassword trên client tự xử
        String username = params.get("username");
        boolean isUsernameExists = this.usersRepo.isUsernameExists(username);

        if (isUsernameExists != true) {
            return 2; // Không tìm thấy username để đổi mật khẩu
        } else {
            Users user = this.getUserByUsername_new(username);

            String oldPassword = params.get("password");
            String newPassword = params.get("newPassword");
            
//            String oldPassword_encode = this.bCryptPasswordEncoder.encode(oldPassword);
//            String currentPassword_encode = user.getPassword();
            
            if (this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            }
            else {
                return 3; // Sai mật khẩu cũ
            }
            
            return this.usersRepo.updateUser(user);
        }
    }
}
