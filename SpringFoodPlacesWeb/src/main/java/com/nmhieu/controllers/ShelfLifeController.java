/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.ShelfLife;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.RestaurantsService;
import com.nmhieu.service.ShelfLifeService;
import com.nmhieu.service.UsersService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;

/**
 *
 * @author HP
 */
@Controller
public class ShelfLifeController {

    @Autowired
    private ShelfLifeService shelfLifeSer;

    @Autowired
    private CustomDateEditor MY_CustomDateEditor;

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private UsersService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, MY_CustomDateEditor);
    }

    @GetMapping("/restaurantManager/shelfLife")
    public String indexShelfLife(Model model, @RequestParam Map<String, String> params, Authentication authentication) throws ParseException {
        String msg = "";
        String restaurantId = params.get("restaurantId");

        if (restaurantId == null || restaurantId.isEmpty()) {
            msg = "Có lỗi xảy ra!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }

        Restaurants restaurant = this.restaurantsService.getRestaurantById(Integer.parseInt(restaurantId));

        if (restaurant == null) {
            msg = "Bạn không sở hữu nhà hàng này!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;
            String username = user.getUsername();
            params.put("username", username);

            Users user_auth = this.userService.getUserByUsername_new(username);

            if (user_auth != null) {
                if (restaurant.getUserId().getUserId().equals(user_auth.getUserId())) {
                    List<ShelfLife> listShelfLife = this.shelfLifeSer.getShelfLife(params);
                    model.addAttribute("shelfLife", new ShelfLife());
                    model.addAttribute("shelfLifes", listShelfLife);
                    model.addAttribute("shelfLifes_All", this.shelfLifeSer.getAllShelfLife(params));
                } else {
                    msg = "Bạn không sở hữu nhà hàng này!";
                    model.addAttribute("msg", msg);
                    return "redirect:/restaurantManager/restaurants";
                }
            }
        }

//        List<ShelfLife> listShelfLife = this.shelfLifeSer.getShelfLife(params);
//        model.addAttribute("shelfLife", new ShelfLife());
//        model.addAttribute("shelfLifes", listShelfLife);
        return "shelfLife";
    }

    @PostMapping("/restaurantManager/shelfLife")
    public String addShelfLife_new(Model model, @ModelAttribute(value = "shelfLife") @Valid ShelfLife shelfLife, BindingResult rs, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";
        if (!rs.hasErrors()) {

            Date fromDate = shelfLife.getFromDate();
            Date toDate = shelfLife.getToDate();
            if (fromDate.after(toDate)) {
                msg = "Ngay bat dau khong duoc lon hon ngay ket thuc";
                model.addAttribute("msg", msg);
                return "redirect:/restaurantManager/shelfLife?restaurantId=" + shelfLife.getRestaurantId().getRestaurantId();
            }

            if (this.shelfLifeSer.addOrUpdateShelfLife(shelfLife) == true) {
                return "redirect:/restaurantManager/shelfLife?restaurantId=" + shelfLife.getRestaurantId().getRestaurantId();
            }
        }
        model.addAttribute("msg", msg);
        return "redirect:/restaurantManager/restaurants";
    }

    //=============================================//
    @GetMapping("/restaurantManager/shelfLife/newShelfLife")
    public String newShelfLife(Model model) {
        model.addAttribute("shelfLife", new ShelfLife());
        return "newShelfLife";
    }

    @PostMapping("/restaurantManager/shelfLife/newShelfLife")
    public String addShelfLife(@ModelAttribute(value = "shelfLife") @Valid ShelfLife shelfLife, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (this.shelfLifeSer.addOrUpdateShelfLife(shelfLife) == true) {
                return "redirect:/restaurantManager/shelfLife";
            }
        }
        return "newShelfLife";
    }

    @GetMapping("/restaurantManager/shelfLife/newShelfLife/{shelflifeId}")
    public String update(Model model, @PathVariable(value = "shelflifeId") int shelflifeId) {
        String msg = "";
        ShelfLife shelfLife = this.shelfLifeSer.getShelfLifeById(shelflifeId);
        if (shelfLife != null) {
            model.addAttribute("shelfLife", this.shelfLifeSer.getShelfLifeById(shelflifeId));
        } 
        else {
            msg = "Thời gian bán không tồn tại!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }
        return "newShelfLife";
    }
}
