<%-- 
    Document   : newUser
    Created on : Aug 19, 2023, 6:47:32 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/newUser.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>

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

<section class="section-infor">
    <c:url value="/admin/users/newUser" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
        <div class="img"><img src="${user.avatar}" alt=""></div>

        <div class="row flex ">
            <div class="col form-floating">

                <form:input type="text" class="form-control" path="firstname" id="firstname" placeholder="Nhập họ... " name="firstname" />
                <label class="float-label" for="firstname">Nhập họ...</label>
            </div>
            <div class="col form-floating">
                <form:input type="text" class="form-control" path="lastname" id="lastname" placeholder="Nhập tên... " name="lastname" />
                <label class="float-label" for="lastname">Nhập tên...</label>
            </div>
        </div>
        <div class="row">
            <div class="col form-floating">
                <form:input type="text" class="form-control" path="phonenumber" id="phonenumber" placeholder="Nhập số điện thoại... " name="phonenumber" />
                <label class="float-label" for="phonenumber">Nhập số điện thoại...</label>
            </div>
        </div>

        <div class="row">
            <div class="col form-floating">
                <form:input type="text" class="form-control" path="email" id="email" placeholder="Nhập email... " name="email" />
                <label class="float-label" for="email">Nhập email...</label>
            </div>
        </div>

        <div class="row">
            <div class="col form-floating">
                <form:input type="text" class="form-control" path="location" id="location" placeholder="Nhập số địa chỉ... " name="location" />
                <label class="float-label" for="phonenumber">Nhập địa chỉ...</label>
            </div>
        </div>

        <c:choose>
            <c:when test="${user.userId == null}">
                <div class="row">
                    <div class="col form-floating">
                        <form:input type="text" class="form-control" path="username" id="username" placeholder="Nhập tên đăng nhập... " name="username" />
                        <label class="float-label" for="username">Nhập tên đăng nhập...</label>
                    </div>
                </div> 

                <div class="row">
                    <div class="col form-floating">
                        <form:input type="password" class="form-control" path="password" id="password" placeholder="Nhập mật khẩu... " name="password" />
                        <label class="float-label" for="password">Nhập mật khẩu... </label>
                    </div>
                </div>

                <div class="row">
                    <div class="col form-floating">
                        <form:input type="password" class="form-control" path="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu... " name="confirmPassword" />
                        <label class="float-label" for="confirmPassword">Nhập lại mật khẩu... </label>
                    </div>
                </div>

            </c:when>
            <c:otherwise>
                <form:hidden path="username" />
                <form:hidden path="password" />
                <form:hidden path="userId" />
                <form:hidden path="avatar" />
                <%--<form:hidden path="location" />--%>
                <form:hidden path="active" />
            </c:otherwise>
        </c:choose>
        <div class="row">
            <div class="col form-floating select-role">
                <form:select class="form-select" id="roles" name="roles" path="roleId">
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${role.roleId == user.roleId.roleId}">
                                <option value="${role.roleId}" selected="${role.roleId}">${role.roleName}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${role.roleId}">${role.roleName}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
                <label for="restaurants" class="float-label">Danh mục Roles</label>
            </div>
        </div>

        <div class="row">
            <div class="col form-floating">
                <form:input type="file" class="form-control" path="file" id="file" name="file" />
                <label for="file" class="float-label">Avatar</label>
            </div>
        </div>
        <div class="row">
            <button type="submit" class="btn-infor"">
                <c:choose>
                    <c:when test="${user.userId == null}">
                        Thêm user
                    </c:when>
                    <c:otherwise>
                        Cập nhật user
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
    </form:form>
</section>


<!--<div class="container">

    <c:url value="/admin/users/newUser" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
        <div>${msg}</div>
        <form:errors path="*" element="div" cssClass="alert alert-danger" />
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="firstname" id="firstname" placeholder="Nhập họ... " name="firstname" />
            <label for="firstname">Nhập họ...</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="lastname" id="lastname" placeholder="Nhập tên... " name="lastname" />
            <label for="lastname">Nhập tên...</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="number" class="form-control" path="phonenumber" id="phonenumber" placeholder="Nhập số điện thoại... " name="phonenumber" />
            <label for="phonenumber">Nhập số điện thoại...</label>
        </div>

        <c:choose>
            <c:when test="${user.userId == null}">
                <div class="form-floating mb-3 mt-3">
                    <form:input type="text" class="form-control" path="username" id="username" placeholder="Nhập tên đăng nhập... " name="username" />
                    <label for="username">Nhập tên đăng nhập...</label>
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:input type="password" class="form-control" path="password" id="password" placeholder="Nhập mật khẩu... " name="password" />
                    <label for="password">Nhập mật khẩu... </label>
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:input type="password" class="form-control" path="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu... " name="confirmPassword" />
                    <label for="confirmPassword">Nhập lại mật khẩu... </label>
                </div>
            </c:when>
            <c:otherwise>
                <form:hidden path="username" />
                <form:hidden path="password" />
                <form:hidden path="confirmPassword" />
                <form:hidden path="userId" />
                <form:hidden path="avatar" />
            </c:otherwise>
        </c:choose>

        <div class="form-floating mb-3 mt-3">
            <form:select class="form-select" id="roles" name="roles" path="roleId">
                <c:forEach items="${roles}" var="role">
                    <c:choose>
                        <c:when test="${role.roleId == user.roleId.roleId}">
                            <option value="${role.roleId}" selected="${role.roleId}">${role.roleName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${role.roleId}">${role.roleName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>

            <label for="restaurants" class="form-label">Danh mục Roles</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="file" class="form-control" path="file" id="file" name="file" />
            <label for="file">Avatar</label>
        </div>

        <button type="submit" class="btn btn-info">
            <c:choose>
                <c:when test="${user.userId == null}">
                    Thêm user
                </c:when>
                <c:otherwise>
                    Cập nhật user
                </c:otherwise>
            </c:choose>
        </button>
    </form:form>
</div>-->
