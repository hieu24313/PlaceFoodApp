<%-- 
    Document   : admin
    Created on : Aug 6, 2023, 2:33:03 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/restaurantManager.css" /> "/>
<c:if test="${not empty param.msg}">
    <div class="toast show">
        <div class="toast-header">
            <h1>ERROR!</h1>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
        </div>
        <div class="toast-body">
            ${param.msg}
        </div>
    </div>
</c:if>

<div class="container ">
    <div class="heading">
        <h1>QUẢN TRỊ HỆ THỐNG</h1>
    </div>
    <div class="res-manager">
        <div class="res-item">
            <a href="<c:url value="/admin/users" />" class="btn btn-info">
                <h3>
                    <i class="fa-solid fa-bowl-food fa-bounce"></i>
                    <br>Quản lý tài khoản
                </h3>
            </a>

        </div>
        <div class="res-item">
            <a href="<c:url value="/admin/restaurants" />" class="btn btn-info">
                <h3>
                    <i class="fa-solid fa-utensils fa-beat-fade"></i>
                    <br>Quản lý nhà hàng
                </h3></a>

        </div>
    </div>
</div>

<!--<div class="container">
    <a href="<c:url value="/admin/users" />" class="btn btn-info">Quản lý users</a>

    <a href="<c:url value="/admin/restaurants" />" class="btn btn-info">Quản lý restaurants</a>

    <a href="<c:url value="/restaurantManager/restaurants" />" class="btn btn-info">Quản lý restaurants</a>

<a href="<c:url value="restaurantManager/restaurants/newRestaurant" />" class="btn btn-info">Quản lý newRestaurant</a>

    <a href="<c:url value="#" />" class="btn btn-info">Quản lý vân vân và mây mây</a>

</div>-->
