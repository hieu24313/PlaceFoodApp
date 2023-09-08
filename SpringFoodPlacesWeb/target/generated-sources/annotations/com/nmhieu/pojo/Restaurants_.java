package com.nmhieu.pojo;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Comments;
import com.nmhieu.pojo.Follow;
import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.Notifications;
import com.nmhieu.pojo.RestaurantStatus;
import com.nmhieu.pojo.ShelfLife;
import com.nmhieu.pojo.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(Restaurants.class)
public class Restaurants_ { 

    public static volatile SetAttribute<Restaurants, Comments> commentsSet;
    public static volatile SetAttribute<Restaurants, Notifications> notificationsSet;
    public static volatile SetAttribute<Restaurants, Fooditems> fooditemsSet;
    public static volatile SingularAttribute<Restaurants, Boolean> confirmationStatus;
    public static volatile SetAttribute<Restaurants, ShelfLife> shelfLifeSet;
    public static volatile SingularAttribute<Restaurants, Boolean> active;
    public static volatile SingularAttribute<Restaurants, String> avatar;
    public static volatile SingularAttribute<Restaurants, RestaurantStatus> restaurantStatus;
    public static volatile SingularAttribute<Restaurants, Integer> restaurantId;
    public static volatile SingularAttribute<Restaurants, String> mapLink;
    public static volatile SingularAttribute<Restaurants, Users> userId;
    public static volatile SetAttribute<Restaurants, CategoriesFood> categoriesFoodSet;
    public static volatile SingularAttribute<Restaurants, String> restaurantName;
    public static volatile SetAttribute<Restaurants, Follow> followSet;
    public static volatile SingularAttribute<Restaurants, String> location;

}