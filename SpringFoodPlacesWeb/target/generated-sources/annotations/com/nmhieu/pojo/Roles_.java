package com.nmhieu.pojo;

import com.nmhieu.pojo.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-09-12T16:31:15")
@StaticMetamodel(Roles.class)
public class Roles_ { 

    public static volatile SetAttribute<Roles, Users> usersSet;
    public static volatile SingularAttribute<Roles, Integer> roleId;
    public static volatile SingularAttribute<Roles, String> roleName;
    public static volatile SingularAttribute<Roles, Boolean> active;

}