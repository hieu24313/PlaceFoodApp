/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.RestaurantsService;
import com.nmhieu.service.UsersService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author HP
 */
@Controller
@PropertySource("classpath:configs.properties")
public class CategoryFoodController {

    @Autowired
    private CategoriesFoodService categoriesFoodSer;

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private UsersService userService;

    @Autowired
    private Environment env;

    @GetMapping("/restaurantManager/categoriesFood")
    public String indexCategories(Model model, @RequestParam Map<String, String> params, Authentication authentication) {

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        int countCate = this.categoriesFoodSer.countCategoriesFood();
        model.addAttribute("counter", Math.ceil(countCate * 1.0 / pageSize));

        String msg = "";
        String pageStr = params.get("page");
        String pageAllStr = params.get("pageAll");
        String restaurantId = params.get("restaurantId");

        if (restaurantId == null || restaurantId.isEmpty()) {
            msg = "Có lỗi xảy ra!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }

        Restaurants restaurant = this.restaurantsService.getRestaurantById(Integer.parseInt(restaurantId));

        if (restaurant == null) {
            msg = "You are not the owner of this restaurant!";
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
                    if (pageStr == null) {
                        if (pageAllStr == null) {
                            params.put("page", "1");
                            model.addAttribute("categories", this.categoriesFoodSer.getCategoriesFood(params));
                        } else {
                            model.addAttribute("categories", this.categoriesFoodSer.getCategoriesFood(params));
                        }
                    } else {
                        model.addAttribute("categories", this.categoriesFoodSer.getCategoriesFood(params));
                    }
                } else {
                    msg = "You are not the owner of this restaurant!";
                    model.addAttribute("msg", msg);
                    return "redirect:/restaurantManager/restaurants";
                }
            }
        }
        model.addAttribute("msg", msg);
        model.addAttribute("cate", new CategoriesFood());
        return "indexCategories";
    }

    @PostMapping("/restaurantManager/categoriesFood")
    public String addCategory_new(Model model, @ModelAttribute(value = "cate") @Valid CategoriesFood cate, BindingResult rs, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";
        if (!rs.hasErrors()) {

            if (categoriesFoodSer.addOrUpdateCate(cate) == true) {
                return "redirect:/restaurantManager/categoriesFood?restaurantId=" + cate.getRestaurantId().getRestaurantId();
            }
        }
        model.addAttribute("msg", msg);
        return "redirect:/restaurantManager/restaurants";
    }

    @GetMapping("/restaurantManager/categoriesFood/newCategoriesFood")
    public String indexNewCate(Model model) {
        model.addAttribute("cate", new CategoriesFood());
        return "newCategory";
    }

    @PostMapping("/restaurantManager/categoriesFood/newCategoriesFood")
    public String addCategory(Model model, @ModelAttribute(value = "cate") @Valid CategoriesFood cate, BindingResult rs, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";

//        String restaurantId = params.get("restaurantId");
//
//        if (restaurantId == null || restaurantId.isEmpty()) {
//            msg = "You are not the owner of this restaurant!";
//            model.addAttribute("msg", msg);
//            return "redirect:/restaurantManager/restaurants";
//        }
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            UserDetails user = (UserDetails) principal;
//            String username = user.getUsername();
//            params.put("username", username);
//
//            Users user_auth = this.userService.getUserByUsername_new(username);
//
//            if (user_auth != null) {
//                cate.setRestaurantId(this.restaurantsService.getRestaurantById(Integer.parseInt(restaurantId)));
//            }
//        }
        if (!rs.hasErrors()) {

            if (categoriesFoodSer.addOrUpdateCate(cate) == true) {
                return "redirect:/restaurantManager/categoriesFood?restaurantId=" + cate.getRestaurantId().getRestaurantId();
            }
        }
        model.addAttribute("msg", msg);
        return "newCategory";
    }

    @GetMapping("/restaurantManager/categoriesFood/newCategoriesFood/{categoryfoodId}")
    public String update(Model model, @PathVariable(value = "categoryfoodId") int categoryfoodId) {
        String msg = "";
        CategoriesFood categoriesFood = this.categoriesFoodSer.getCategoryById(categoryfoodId);
        if (categoriesFood != null) {
            model.addAttribute("cate", this.categoriesFoodSer.getCategoryById(categoryfoodId));
        } else {
            msg = "Danh mục món ăn không tồn tại!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }

        return "newCategory";
    }

}
