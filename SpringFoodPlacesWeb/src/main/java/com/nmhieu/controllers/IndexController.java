/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Users;
import com.nmhieu.service.RolesService;
import com.nmhieu.service.UsersService;
//import com.nmhieu.twillio.TwillioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HP
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
public class IndexController {

    // Có service rồi thì chỉ wire từ service thôi OK
    // Truy vấn HQL ở đây = cúc nha
    @Autowired
    private RolesService rolesService;

    @Autowired
    private UsersService userService;

    @Autowired
    private Environment environment;

//    @Autowired
//    private TwillioService tws;
    
    @ModelAttribute
    public void commonAttr(Model model,@RequestParam Map<String, String> params, Authentication authentication) {
        model.addAttribute("roles", this.rolesService.getRoles());
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;
            String username = user.getUsername();
            params.put("username", username);

            Users user_auth = this.userService.getUserByUsername_new(username);

            if (user_auth != null) {
                model.addAttribute("current_user", user_auth);
            }
        }
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;
            String username = user.getUsername();
            params.put("username", username);
//            tws.send_OTP("+84359505026");
            Users user_auth = this.userService.getUserByUsername_new(username);

            if (user_auth != null) {
                model.addAttribute("current_user", user_auth);
            }
        }
        return "index";
    }
}
