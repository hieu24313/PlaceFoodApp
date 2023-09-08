package com.nmhieu.pojo;

import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(Follow.class)
public class Follow_ { 

    public static volatile SingularAttribute<Follow, Integer> followId;
    public static volatile SingularAttribute<Follow, Users> userIdIndex;
    public static volatile SingularAttribute<Follow, Restaurants> restaurantIdIndex;

}