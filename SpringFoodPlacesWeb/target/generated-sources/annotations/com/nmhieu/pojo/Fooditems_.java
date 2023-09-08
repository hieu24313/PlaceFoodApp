package com.nmhieu.pojo;

import com.nmhieu.pojo.CategoriesFood;
import com.nmhieu.pojo.Comments;
import com.nmhieu.pojo.ReceiptDetail;
import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.ShelfLife;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(Fooditems.class)
public class Fooditems_ { 

    public static volatile SetAttribute<Fooditems, Comments> commentsSet;
    public static volatile SingularAttribute<Fooditems, String> foodName;
    public static volatile SingularAttribute<Fooditems, CategoriesFood> categoryfoodId;
    public static volatile SingularAttribute<Fooditems, BigDecimal> price;
    public static volatile SingularAttribute<Fooditems, Integer> foodId;
    public static volatile SingularAttribute<Fooditems, Boolean> available;
    public static volatile SingularAttribute<Fooditems, String> description;
    public static volatile SingularAttribute<Fooditems, Boolean> active;
    public static volatile SingularAttribute<Fooditems, String> avatar;
    public static volatile SingularAttribute<Fooditems, Restaurants> restaurantId;
    public static volatile SetAttribute<Fooditems, ReceiptDetail> receiptDetailSet;
    public static volatile SingularAttribute<Fooditems, ShelfLife> shelflifeId;

}