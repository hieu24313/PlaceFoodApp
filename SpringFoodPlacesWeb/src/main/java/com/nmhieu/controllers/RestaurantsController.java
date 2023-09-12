/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.ReceiptDetailAndReceipt;
import com.nmhieu.pojo.Receipts;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Roles;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.CategoriesFoodService;
import com.nmhieu.service.FollowService;
import com.nmhieu.service.FoodItemsService;
import com.nmhieu.service.ReceiptDetailService;
import com.nmhieu.service.ReceiptService;
import com.nmhieu.service.RestaurantStatusService;
import com.nmhieu.service.RestaurantsService;
import com.nmhieu.service.StatsService;
import com.nmhieu.service.UsersService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private UsersService userService;

    @Autowired
    private RestaurantStatusService restaurantStatusService;

    @Autowired
    private CategoriesFoodService categoryFoodService;

    @Autowired
    private FoodItemsService foodItemsService;

    @Autowired
    private Environment environment;

    @Autowired
    private ReceiptDetailService receiptDetailService;

    @Autowired
    private ReceiptService receiptService;
    
    @Autowired
    private StatsService statsService;
    
    @Autowired
    private FollowService followService;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("restaurantStatus_list", this.restaurantStatusService.getRestaurantsStatus());
        model.addAttribute("user_list", this.userService.getUsers(null));

    }

    @GetMapping("/restaurantManager/restaurants")
    public String list(Model model, @RequestParam Map<String, String> params, Authentication authentication) {
        int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE"));
        String confirm = params.get("confirm");
        params.put("confirm", confirm);
        int countRestaurant = this.restaurantsService.countRestaurants(params);
        model.addAttribute("counter", Math.ceil(countRestaurant * 1.0 / pageSize));

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;
            String username = user.getUsername();
            params.put("username", username);

            Users user_auth = this.userService.getUserByUsername_new(username);

            if (user_auth != null) {
                params.put("current_user_UserId", user_auth.getUserId().toString());
                int countRestaurant_UserId = this.restaurantsService.countRestaurants(params);
                model.addAttribute("counter", Math.ceil(countRestaurant_UserId * 1.0 / pageSize));
            }

            model.addAttribute("role", user_auth.getRoleId().getRoleId());
        }

        String pageStr = params.get("page");
        String pageAllStr = params.get("pageAll");

        if (pageStr == null) {
            if (pageAllStr == null) {
                params.put("page", "1");

                model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
            } else {
                model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
            }

        } else {
            model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
        }

        return "restaurants";
    }

    @GetMapping("/restaurantManager/restaurants/newRestaurant")
    public String newRestaurant(Model model) {
        model.addAttribute("restaurant", new Restaurants());
        return "newRestaurant";
    }

//  Cái restaurantId trong cái GetMapping này là trùng với bên jsp nha :)
    @GetMapping("/restaurantManager/restaurants/{restaurantId}")
    public String update(Model model, @PathVariable(value = "restaurantId") int restaurantId, @RequestParam Map<String, String> params, Authentication authentication) {
        String msg = "";
        Restaurants restaurant = this.restaurantsService.getRestaurantById(restaurantId);

        if (restaurant != null) {
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                UserDetails user = (UserDetails) principal;
                String username = user.getUsername();
                params.put("username", username);

                Users user_auth = this.userService.getUserByUsername_new(username);

                if (user_auth != null) {
                    if (restaurant.getUserId().getUserId().equals(user_auth.getUserId())) {
                        model.addAttribute("restaurant", this.restaurantsService.getRestaurantById(restaurantId));
                        model.addAttribute("category_list", this.categoryFoodService.getCategoriesFoodByRestaurantId(restaurantId));
                        params.put("restaurantId", String.valueOf(restaurantId));
                        List<Fooditems> food_list = this.foodItemsService.getFoodItems(params);
                        model.addAttribute("food_list", food_list);

                        List<ReceiptDetail> receiptDetails_list = new ArrayList<>();
                        for (Fooditems food : food_list) {
                            receiptDetails_list.addAll(this.receiptDetailService.getReceiptDetailsByFoodId(food.getFoodId()));
                        }

                        List<Receipts> receipts_List = new ArrayList<>();

                        for (ReceiptDetail receiptDetail : receiptDetails_list) {
                            int receiptId = receiptDetail.getReceiptId().getReceiptId();
                            Receipts receipt = this.receiptService.getReceiptById(receiptId);
                            receipts_List.add(receipt);
                        }

                        List<ReceiptDetailAndReceipt> receiptDetailPerfect = new ArrayList<>();
                        for (int i = 0; i < receiptDetails_list.size(); i++) {
                            ReceiptDetailAndReceipt rdp = new ReceiptDetailAndReceipt();
                            rdp.setReceiptId(receipts_List.get(i).getReceiptId());
                            rdp.setFoodName(receiptDetails_list.get(i).getFooditemId().getFoodName());
                            rdp.setPrice(receiptDetails_list.get(i).getUnitPrice());
                            rdp.setQuantity(receiptDetails_list.get(i).getQuantity());
                            rdp.setAmount(receiptDetails_list.get(i).getAmount());
                            rdp.setCreatedDate(receipts_List.get(i).getReceiptDate());
                            rdp.setStatusReceiptId(receipts_List.get(i).getStatusReceiptId().getStatusReceiptId());
                            rdp.setStatusReceipt(receipts_List.get(i).getStatusReceiptId().getStatusReceipt());
                            rdp.setLocation(receipts_List.get(i).getUserId().getLocation());
                            receiptDetailPerfect.add(rdp);
                        }

                        model.addAttribute("statsFood", this.statsService.statsRevenue(params));
                        model.addAttribute("statsFoodByCate", this.statsService.statsRevenueByCate(params));
                        model.addAttribute("receiptDetails_list", receiptDetails_list);
                        model.addAttribute("receipts_List", receipts_List);
                        model.addAttribute("receiptDetailPerfect_list", receiptDetailPerfect);
                        model.addAttribute("followers", this.followService.getFollowByRestaurantId(restaurantId).size());

                    } else {
                        msg = "You are not the owner of this restaurant!";
                        model.addAttribute("msg", msg);
                        return "redirect:/restaurantManager/restaurants";
                    }
                }
            }
        } else {
            msg = "You are not the owner of this restaurant!";
            model.addAttribute("msg", msg);
            return "redirect:/restaurantManager/restaurants";
        }

        model.addAttribute("msg", msg);
        return "newRestaurant";
    }

    @PostMapping("/restaurantManager/restaurants/newRestaurant")
    public String add(@RequestParam Map<String, String> params, @ModelAttribute(value = "restaurant") @Valid Restaurants restaurant, BindingResult rs, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            UserDetails user = (UserDetails) principal;
            String username = user.getUsername();
            params.put("username", username);

            Users user_auth = this.userService.getUserByUsername_new(username);

            if (user_auth != null) {
                restaurant.setUserId(user_auth);
            }
        }

        if (!rs.hasErrors()) {
            if (restaurantsService.addOrUpdateRestaurants(restaurant) == true) {
                return "redirect:/restaurantManager/restaurants";
            }
        }
        return "newRestaurant";
    }

    @GetMapping("/admin/restaurants")
    public String restaurant_admin(Model model, @RequestParam Map<String, String> params) {
        int pageSize = Integer.parseInt(this.environment.getProperty("PAGE_SIZE"));
        String confirm = params.get("confirm");
        params.put("confirm", confirm);
        int countRestaurant = this.restaurantsService.countRestaurants(params);
        model.addAttribute("counter", Math.ceil(countRestaurant * 1.0 / pageSize));

        String pageStr = params.get("page");
        String pageAllStr = params.get("pageAll");

        if (pageStr == null) {
            if (pageAllStr == null) {
                params.put("page", "1");

                model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
            } else {
                model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
            }

        } else {
            model.addAttribute("restaurant_list", this.restaurantsService.getRestaurants(params));
        }

        return "restaurants_admin";
    }

    @GetMapping("/admin/restaurants/newRestaurant")
    public String newRestaurant_admin(Model model) {
        model.addAttribute("restaurant", new Restaurants());
        return "newRestaurant_admin";
    }

//  Cái restaurantId trong cái GetMapping này là trùng với bên jsp nha :)
    @GetMapping("/admin/restaurants/{restaurantId}")
    public String update_admin(Model model, @PathVariable(value = "restaurantId") int restaurantId) {
        model.addAttribute("restaurant", this.restaurantsService.getRestaurantById(restaurantId));
        return "newRestaurant_admin";
    }

    @PostMapping("/admin/restaurants/newRestaurant")
    public String add_admin(@ModelAttribute(value = "restaurant") @Valid Restaurants restaurant, BindingResult rs) {

        if (!rs.hasErrors()) {
            if (restaurantsService.addOrUpdateRestaurants(restaurant) == true) {
                return "redirect:/admin/restaurants";
            }
        }
        return "newRestaurant_admin";
    }
}
