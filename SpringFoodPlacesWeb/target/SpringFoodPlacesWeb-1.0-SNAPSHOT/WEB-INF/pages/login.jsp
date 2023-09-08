<%-- 
    Document   : login
    Created on : Aug 11, 2023, 10:12:07 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/register.css" /> "/>
<c:url value="/login" var="action" />

<c:if test="${param.accessDenied != null}">
    <div>
        Không có quyền
    </div>
</c:if>

<!--<form class="container" method="post" action="${action}">
    <div class="form-floating mb-3 mt-3">
        <input type="text" class="form-control" id="username" placeholder="Nhập username" name="username">
        <label for="username">Nhập tên đăng nhập</label>
    </div>

    <div class="form-floating mt-3 mb-3">
        <input type="text" class="form-control" id="password" placeholder="Nhập password" name="password">
        <label for="password">Nhập mật khẩu</label>
    </div>

    <div class="form-floating mt-3 mb-3">
        <input type="submit" value="Đăng nhập" class="btn btn-danger">
    </div>
    <div class="g-signin2" data-onsuccess="onSignIn"></div>
    <script>
        function onSignIn(googleUser) {
            var profile = googleUser.getBasicProfile();
            console.log("ID: " + profile.getId());
            console.log("Name: " + profile.getName());
            console.log("Image URL: " + profile.getImageUrl());
            console.log("Email: " + profile.getEmail());
        }
    </script>
</form>-->

<div class="header">

    <div class="header-main">
        <h1>ĐĂNG NHẬP</h1>
        <div class="header-bottom">
            <div class="header-right w3agile">

                <div class="header-left-bottom agileinfo">

                    <form method="post" action="${action}">
                        <input type="text" class="form-control" id="username" placeholder="Nhập username" name="username">
                        <input type="text" class="form-control" id="password" placeholder="Nhập password" name="password">

                        <div class="remember">
                            <span class="checkbox1">
                                <label class="checkbox"><input type="checkbox" name="" checked=""><i> </i>Remember
                                    me</label>
                            </span>
                            <div class="forgot">
                                <h6><a href="#">Forgot Password?</a></h6>
                            </div>
                            <div class="clear"> </div>
                        </div>

                        <input type="submit" value="Register">
                    </form>
<!--                    <div class="header-left-top">
                        <div class="sign-up">
                            <h2>or</h2>
                        </div>

                    </div>-->
<!--                    <div class="header-social wthree login-gg">

                        <a href="#" class="face">
                            <img src="images/gg.png" class="imggg">
                            <h5>google</h5>
                        </a>
                    </div>-->

                </div>
            </div>

        </div>
    </div>
</div>


