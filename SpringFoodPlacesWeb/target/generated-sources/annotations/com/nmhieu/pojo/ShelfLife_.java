package com.nmhieu.pojo;

import com.nmhieu.pojo.Fooditems;
import com.nmhieu.pojo.Restaurants;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(ShelfLife.class)
public class ShelfLife_ { 

    public static volatile SingularAttribute<ShelfLife, String> shelflifeName;
    public static volatile SingularAttribute<ShelfLife, Date> fromDate;
    public static volatile SetAttribute<ShelfLife, Fooditems> fooditemsSet;
    public static volatile SingularAttribute<ShelfLife, Date> toDate;
    public static volatile SingularAttribute<ShelfLife, Boolean> active;
    public static volatile SingularAttribute<ShelfLife, Restaurants> restaurantId;
    public static volatile SingularAttribute<ShelfLife, Integer> shelflifeId;

}