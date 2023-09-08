/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.FoodItemsService;
import com.nmhieu.service.RestaurantsService;
import com.nmhieu.service.ShelfLifeService;
import com.nmhieu.service.UsersService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
public class FoodItemsController {

    @Autowired
    private FoodItemsService foodItemSer;

    @Autowired
    private CategoriesFoodService categoryFoodSer;

    @Autowired
    private UsersService userService;

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private ShelfLifeService shelfLifeSer;

    @Autowired
    private Environment environment;

    @ModelAttribute
    public void commonAttr(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("shelfLife_list", this.shelfLifeSer.getShelfLife(params));
        model.addAttribute("category_list", this.categoryFoodSer.getCategoriesFood(params));
    }

    @GetMapping("/restaurantManager/foodItems")
    public String indexFoodItems(Model model, @RequestParam Map<String, String> params, Authentication authentication) {
        model.addAttribute("foodItems", this.foodItemSer.getFoodItems(params));

        int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE"));
        int countFoodItems = this.foodItemSer.countFoodItems(params);
        model.addAttribute("counter", Math.ceil(countFoodItems * 1.0 / pageSize));

        String pageStr = params.get("page");
        String pageAllStr = params.get("pageAll");

        String msg = "";
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
                            model.addAttribute("foodItems", this.foodItemSer.getFoodItems(params));
                        } else {
                            model.addAttribute("foodItems", this.foodItemSer.getFoodItems(params));
                        }

                    } else {
                        model.addAttribute("foodItems", this.foodItemSer.getFoodItems(params));
                    }
                } else {
                    msg = "You are not the owner of this restaurant!";
                    model.addAttribute("msg", msg);
                    return "redirect:/restaurantManager/restaurants";
                }
            }
        }

        model.addAttribute("foodItem", new Fooditems());
        return "indexFoodItems";
    }

    @PostMapping("/restaurantManager/foodItems")
    public String addFoodItems_new(Model model, @ModelAttribute(value = "foodItem") @Valid Fooditems foodItem, BindingResult rs, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";
        if (!rs.hasErrors()) {

            if (this.foodItemSer.addOrUpdateFoodItem(foodItem) == true) {
                return "redirect:/restaurantManager/foodItems?restaurantId=" + foodItem.getRestaurantId().getRestaurantId();
            }
        }
        model.addAttribute("msg", msg);
        return "redirect:/restaurantManager/restaurants";
    }

//    @RequestMapping("/restaurantManager/foodItems")
//    public String indexFoodItemsTest(Model model, @RequestParam Map<String, String> params){
//        model.addAttribute("foodItems", this.foodItemSer.getFoodItems(params));
//        model.addAttribute("msg", "Chào");
//        return "indexFoodItems";
//    }
    @GetMapping("/restaurantManager/foodItems/newFoodItems")
    public String newFoodItems(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("foodItem", new Fooditems());
        model.addAttribute("shelfLife_list", this.shelfLifeSer.getShelfLife(params));
        model.addAttribute("category_list", this.categoryFoodSer.getCategoriesFood(params));
        return "newFoodItems";
    }

    @PostMapping("/restaurantManager/foodItems/newFoodItems")
    public String addFood(Model model, @ModelAttribute(value = "foodItem") @Valid Fooditems food, BindingResult rs, Authentication authentication, @RequestParam Map<String, String> params) {
        String msg = "";
        if (!rs.hasErrors()) {
            if (this.foodItemSer.addOrUpdateFoodItem(food) == true) {
                if (food.getRestaurantId().getRestaurantId() != null) {
                    return "redirect:/restaurantManager/restaurants/" + food.getRestaurantId().getRestaurantId();
                } else {
                    msg = "Có lỗi xảy ra!";
                }

            }

        }

        model.addAttribute("msg", msg);
        return "newFoodItems";
    }

    @GetMapping("/restaurantManager/foodItems/{foodId}")
    public String update(Model model, @PathVariable(value = "foodId") int foodId, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";
        // TRỜI ƠI CÁI CHỖ NÀY :)))))))))))))
        String restaurantId = params.get("restaurantId");
        if (restaurantId == null || restaurantId.isEmpty()) {
            msg = "You are not the owner of this restaurant!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        } else {
            Restaurants restaurantParam = this.restaurantsService.getRestaurantById(Integer.parseInt(restaurantId));

            Fooditems food = this.foodItemSer.getFoodItemById(foodId);
            if (food != null) {
                Restaurants restaurant = this.restaurantsService.getRestaurantById(food.getRestaurantId().getRestaurantId());

                // LÀM TỐN 4 TIẾNG CỦA TAO :)))))))))))))
                if (!restaurant.getRestaurantId().equals(restaurantParam.getRestaurantId())) {
                    msg = "Không tồn tại món này trong nhà hàng của bạn!";
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
                            model.addAttribute("foodItem", this.foodItemSer.getFoodItemById(foodId));

                        } else {
                            // HOW TO GỬI CÁI NÀY RA LẠI TRANG restaurants đây :) 
                            // NÓ CỘNG CHUỖI Ở TRÊN URL BÀ CON ƠI :)
                            ///restaurantManager/restaurants?msg=Kh%F4ng+t%3Fn+t%3Fi+m%F3n+n%E0y+trong+nh%E0+h%E0ng+c%3Fa+b%3Fn%21
                            msg = "Không tồn tại món này trong nhà hàng của bạn!";
                            model.addAttribute("msg", msg);
                            return "redirect:/restaurantManager/restaurants";
                        }
                    }
                }
            } else {
                msg = "Không tồn tại món này trong nhà hàng của bạn!";
                model.addAttribute("msg", msg);
                return "redirect:/restaurantManager/restaurants";
            }
        }
        model.addAttribute("msg", msg);
        return "newFoodItems";
    }
}
