package com.nmhieu.pojo;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.Restaurants;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(CategoriesFood.class)
public class CategoriesFood_ { 

    public static volatile SetAttribute<CategoriesFood, Fooditems> fooditemsSet;
    public static volatile SingularAttribute<CategoriesFood, Integer> categoryfoodId;
    public static volatile SingularAttribute<CategoriesFood, Boolean> active;
    public static volatile SingularAttribute<CategoriesFood, String> categoryname;
    public static volatile SingularAttribute<CategoriesFood, Restaurants> restaurantId;

}