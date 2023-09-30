<%-- 
    Document   : header
    Created on : Jul 31, 2023, 9:35:50 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href=" <c:url value="/css/header.css" /> "/>

<header class="header">
    <div class="header header__left">
        <img src="https://res.cloudinary.com/dhwuwy0to/image/upload/v1693039991/ljj0hlsds6ch1ngcxxfe.png" alt="">
        <c:url value="/" var = "action" />
        <a href="${action}">IM'PROOK</a>
    </div>
    <div class="header header__middle">
        <c:choose>
            <c:when test="${pageContext.request.isUserInRole('ROLE_Admin')}">
                <div>

                    <a href="<c:url value="/admin" />"><button>Quản trị hệ thống </button></a>

                </div>
            </c:when>
            
            <c:when test="${pageContext.request.isUserInRole('ROLE_SupAdmin')}">
                <div>

                    <a href="<c:url value="/admin" />"><button>Quản trị hệ thống </button></a>

                </div>
            </c:when>

            <c:when test="${pageContext.request.isUserInRole('ROLE_RestaurantManager')}">
                <div>
                    <a href="<c:url value="/restaurantManager" />"><button>Quản trị nhà hàng </button></a>
                </div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <!--                <div>
                                    <button>
                                        <a href="<c:url value="/" />">${pageContext.request.userPrincipal.principal.username}</a>
                                    </button>
                                </div>
                
                                <div >
                                    <button>
                                        <a href="<c:url value="/" />">${pageContext.request.userPrincipal.principal.authorities}</a>
                                    </button>
                                </div>-->

                <div>
                    <a href="<c:url value="/logout" />"><button>Đăng xuất </button></a>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <a href="<c:url value="/login" />"><button>Đăng nhập</button></a>
                </div>
<!--                <div >
                    <a href="<c:url value="/register" />"><button>Đăng ký</button></a>
                </div>-->
            </c:otherwise>
        </c:choose>


    </div>
    <div class="header header__right">
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <div class="avatar">
                    <a href="<c:url value="/" />">
                        <c:choose>
                            <c:when test="${current_user.avatar != null and not empty current_user.avatar}">
                                <img src="${current_user.avatar}" alt="avatar của ${current_user.lastname}"/>
                            </c:when>
                            <c:otherwise>
                                 <img src="<c:url value="/images/usernull.png" />" alt="avatar của ${current_user.lastname}"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </div>

                <div>

                    <a href="<c:url value="/" />"> <button>${pageContext.request.userPrincipal.principal.username}</button></a>

                </div>
            </c:when>
        </c:choose>
    </div>
</header>




<!--<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">WEB CỦA TUI</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">

                <li class="nav-item">
<c:url value="/" var = "action" />
<a class="nav-link" href="${action}">Trang chủ</a>
</li>

<c:choose>
    <c:when test="${pageContext.request.isUserInRole('ROLE_Admin')}">
        <c:forEach items="${roles}" var = "role">

            <c:url value="/" var = "rolesAction">
                <c:param name="roleId" value="${role.roleId}" />
            </c:url>
            <li class="nav-item">
                <a class="nav-link" href="${rolesAction}">${role.roleId} -  ${role.roleName}</a>
            </li>

        </c:forEach>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/" />">${pageContext.request.userPrincipal.principal.username}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/" />">${pageContext.request.userPrincipal.principal.authorities}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/logout" />">Đăng xuất nè</a>
        </li>
    </c:when>
    <c:otherwise>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/login" />">Đăng nhập nè</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/register" />">Đăng ký nè</a>
        </li>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${pageContext.request.isUserInRole('ROLE_Admin')}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/admin" />">Admin</a>
        </li>
    </c:when>

    <c:when test="${pageContext.request.isUserInRole('ROLE_RestaurantManager')}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/restaurantManager" />">restaurantManager</a>
        </li>
    </c:when>
</c:choose>


</ul>
<form class="d-flex" action="${action}">
<input class="form-control me-2" type="text" name="keyword" placeholder="Nhập gì đi... ">
<button class="btn btn-primary" type="submit">Tìm</button>
</form>
</div>
</div>

</nav>-->