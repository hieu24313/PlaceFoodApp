package com.nmhieu.pojo;

import com.nmhieu.pojo.Restaurants;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(RestaurantStatus.class)
public class RestaurantStatus_ { 

    public static volatile SetAttribute<RestaurantStatus, Restaurants> restaurantsSet;
    public static volatile SingularAttribute<RestaurantStatus, Integer> statusId;
    public static volatile SingularAttribute<RestaurantStatus, Boolean> active;
    public static volatile SingularAttribute<RestaurantStatus, String> restaurantStatus;

}