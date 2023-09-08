<%-- 
    Document   : registerController
    Created on : Aug 2, 2023, 8:44:16 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/register.css" /> "/>
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

<c:if test="${not empty msg}">
    <div class="toast show">
        <div class="toast-header">
            <h1>ERROR!</h1>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
        </div>
        <div class="toast-body">
            ${msg}
        </div>
    </div>
</c:if>
<div class="header">

    <div class="header-main">
        <h1>ĐĂNG KÝ</h1>
        <div class="header-bottom">
            <div class="header-right w3agile">

                <div class="header-left-bottom agileinfo">

<!--                    <div>${msg}</div>-->
                    <c:url value="/register" var="action"/>
                    <form:form method="post" modelAttribute="user" action="${action}">

                        <form:input type="text" class="form-control" path="username" id="username" placeholder="Nhập tên đăng nhập... " name="username" />

                        <form:input type="password" class="form-control" path="password" id="password" placeholder="Nhập mật khẩu... " name="password" />

                        <form:input type="password" class="form-control" path="confirmPassword" id="confirmPassword" placeholder="Xác nhận mật khẩu... " name="confirmPassword" />

                        <%--<form:input type="text" class="form-control" path="phonenumber" id="phonenumber" placeholder="Nhập số điện thoại... " name="phonenumber" />--%>

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
                    </form:form>
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
