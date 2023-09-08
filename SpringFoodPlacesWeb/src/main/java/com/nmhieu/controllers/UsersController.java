/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.RolesService;
import com.nmhieu.service.UsersService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HP
 */
@Controller
@ControllerAdvice
public class UsersController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Environment environment;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("roles", this.rolesService.getRoles());
    }

    @RequestMapping("/admin/users")
    public String users(Model model, @RequestParam Map<String, String> params) {
        int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE_USERS"));
        int countUsers = this.usersService.countUsers();
        model.addAttribute("counter", Math.ceil(countUsers * 1.0 / pageSize));

        String pageStr = params.get("page");
        String pageAllStr = params.get("pageAll");

        if (pageStr == null) {

            if (pageAllStr == null) {
                params.put("page", "1");
                model.addAttribute("users_list", this.usersService.getUsers(params));
            } else {
                model.addAttribute("users_list", this.usersService.getUsers(params));
            }

        } else {
            model.addAttribute("users_list", this.usersService.getUsers(params));
        }

        return "users";
    }

    @GetMapping("/admin/users/newUser")
    public String newRestaurant(Model model) {
        model.addAttribute("user", new Users());
        return "newUser";
    }

//  Cái userId trong cái GetMapping này là trùng với bên jsp nha :)
    @GetMapping("/admin/users/{userId}")
    public String detailUser(Model model, @PathVariable(value = "userId") int userId) {
        String msg = "";
        Users user = this.usersService.getUserById(userId);
        if (user == null) {
            model.addAttribute("msg", msg);
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "newUser";
    }

//    @PostMapping("/admin/users/newUser")
//    public String add(Model model, @ModelAttribute(value = "user") @Valid Users user, BindingResult rs) {
//        String msg = "";
//        if (!rs.hasErrors()) {
//            // update
//            if (user.getUserId() != null) {
//                if (this.usersService.addOrUpdateUsers(user) == true) {
//                    return "redirect:/admin/users";
//                }
//                else {
//                    msg = "Số điện thoại hoặc email đã được đăng ký";
//                    model.addAttribute("msg", msg);
//                    return "newUser";
//                }
//            //add
//            } else {
//                if (user.getPassword().equals(user.getConfirmPassword())) {
//                    if (this.usersService.addOrUpdateUsers(user) == true) {
//                        return "redirect:/admin/users";
//                    }
//                    else {
//                        msg = "OK BUGS";
//                    }
//                }
//                else {
//                    msg = "Mật khẩu không khớp";
//                }
//            }
//        }
//        model.addAttribute("msg", msg);
//        return "newUser";
//    }
//}
    @PostMapping("/admin/users/newUser")
    public String addOrUpdate(Model model, @ModelAttribute(value = "user") @Valid Users user, BindingResult rs) {
        String msg = "";
        if (!rs.hasErrors()) {
            // add
            if (user.getUserId() == null) {
                if (user.getPassword().equals(user.getConfirmPassword())) {
                    int check = this.usersService.addUser_server(user);
                    if (check == 1) {
                        msg = "Thêm thành công tài khoản!";
                        model.addAttribute("msg", msg);
                        return "redirect:/admin/users";
                    }
                    if (check == 2) {
                        msg = "Số điện thoại đã được đăng ký!";
                    }

                    if (check == 3) {
                        msg = "Email đã được đăng ký!";
                    }

                    if (check == 4) {
                        msg = "Tài khoản đã được đăng ký!";
                    }
                } else {
                    msg = "Mật khẩu không khớp!";
                }
                //update
            } else {
                int check = this.usersService.updateUser_server(user);
                if (check == 1) {
                    msg = "Cập nhật thành công tài khoản!";
                    model.addAttribute("msg", msg);
                    return "redirect:/admin/users";
                }
                if (check == 2) {
                    msg = "Số điện thoại đã được đăng ký!";
                }
                if (check == 3) {
                    msg = "Email đã được đăng ký!";
                }
                if (check == 4) {
                    msg = "Không tìm thấy tài khoản này!";
                }
            }

        }
        model.addAttribute("msg", msg);
        return "newUser";
    }
}
