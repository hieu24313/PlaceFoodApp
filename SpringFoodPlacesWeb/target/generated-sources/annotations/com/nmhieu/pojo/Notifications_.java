package com.nmhieu.pojo;

import com.nmhieu.pojo.Restaurants;
import com.nmhieu.pojo.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-08T21:22:57")
@StaticMetamodel(Notifications.class)
public class Notifications_ { 

    public static volatile SingularAttribute<Notifications, Boolean> active;
    public static volatile SingularAttribute<Notifications, Integer> notificationId;
    public static volatile SingularAttribute<Notifications, String> notificationType;
    public static volatile SingularAttribute<Notifications, Restaurants> restaurantId;
    public static volatile SingularAttribute<Notifications, Users> userId;
    public static volatile SingularAttribute<Notifications, Date> notificationDate;

}