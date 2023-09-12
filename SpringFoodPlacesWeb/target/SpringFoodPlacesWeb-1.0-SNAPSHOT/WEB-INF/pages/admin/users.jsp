<%-- 
    Document   : users
    Created on : Jul 31, 2023, 9:52:17 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/js/user.js" />"></script>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/user.css" /> "/>

<h1 class="mt-5 mb-5" style="text-align: center; font-weight: bold">QUẢN TRỊ TÀI KHOẢN</h1>

<c:url value="/admin/users" var = "action" />
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<c:if test="${not empty param.msg}">
    <div class="toast show">
        <div class="toast-header">
            <h1>THÔNG BÁO!</h1>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
        </div>
        <div class="toast-body">
            ${param.msg}
        </div>
    </div>
</c:if>

<c:if test="${param.accessDenied != null}">
    <div class="alert alert-danger alert-dismissible fade show">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <strong>Danger!</strong> BẠN KHÔNG CÓ QUYỀN Ở ĐÂY!
    </div>
</c:if>

<!--<div>
    <a href="users/newUser" class = "btn btn-success"> Thêm user </a>
</div>

<c:if test="${counter > 1}">
    <ul class="pagination mt-1">
    <c:url value="/admin/users" var="pageAction">
        <c:param name="pageAll"></c:param>
    </c:url>
    <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả user</a></li>

    <c:forEach begin="1" end="${counter}" var = "i">
        <c:url value="/admin/users" var="pageAction">
            <c:param name="page" value="${i}"></c:param>
        </c:url>
        <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
    </c:forEach>
</ul>
</c:if>
<div></div>-->

<section>
    <c:if test="${counter > 1}">
        <div class = "page-size">

            <ul class="">
                <c:url value="/admin/users" var="pageAction">
                    <c:param name="pageAll"></c:param>
                </c:url>

                <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả tài khoản</a></li>

                <c:choose>
                    <c:when test="${param.confirm eq 'false'}">
                        <c:forEach begin="1" end="${counter}" var = "i">
                            <c:url value="/admin/users" var="pageAction">
                                <c:param name="confirm" value="false" />
                                <c:param name="page" value="${i}"></c:param>

                            </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:when>

                    <c:when test="${param.confirm eq 'true'}">
                        <c:forEach begin="1" end="${counter}" var = "i">
                            <c:url value="/admin/users" var="pageAction">
                                <c:param name="confirm" value="true" />
                                <c:param name="page" value="${i}"></c:param>

                            </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="${counter}" var = "i">
                                <c:url value="/admin/users" var="pageAction">
                                    <c:param name="page" value="${i}"></c:param>
                                </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </div>
    </c:if>

    <c:if test="${counter <= 1}">
        <div class = "page-size">
            <ul class="">
                <c:url value="/admin/users" var="pageAction">
                    <c:param name="pageAll"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả tài khoản</a></li>   
            </ul>
        </div>
    </c:if>

    <div class = "page-size">
        <ul class="">
            <c:url value="/admin/users" var="pageAction">
                <c:param name="pageAll"></c:param>
            </c:url>
            <li class="page-item"><a href="users/newUser" class = "btn btn-success"> TẠO TÀI KHOẢN </a></li>  
        </ul>
    </div>

</section>

<section>
    <div class="row mx-auto">
        <c:forEach items="${users_list}" var = "user">
            <div class="col-lg-3 col-md-6 col-sm-12">

                <div class="course_card">
                    <a class ="detail-restaurant" href="<c:url value="/admin/users/${user.userId}" />">
                        <div class="course_card_img">
                            <img ,="" alt="avatar của ${user.firstname} ${user.lastname}" src="${user.avatar}" />
                        </div>
                        <div class="course_card_content">
                            <h3 class="title">
                                <strong>${user.firstname} ${user.lastname} (${user.userId})</strong>
                            </h3>

                            <h5 class="title">
                                Số điện thoại: ${user.phonenumber}
                            </h5>

                            <h5 class="title">
                                Địa chỉ: ${user.location}
                            </h5>

                            <p class="description">

                            </p>

                        </div>
                    </a>
                    <div class="course_card_footer">
                        <!--<a class="nav-item" href="<c:url value="/admin/restaurants/${restaurant.restaurantId}" />">Xem chi tiết</a>-->
                        <c:url value="/api/server/admin/users/${user.userId}" var="userPathAPI"/>

                        <a class="nav-item delete-button" onclick="deleteUser('${userPathAPI}', ${user.userId})" href="javascript:;">Xóa người dùng</a>
                    </div>
                </div>

            </div>
        </c:forEach>
    </div>

</section>

<!--<table class="table table-hover container">
    <thead>
        <tr>
            <th>Avatar</th>
            <th>id</th>
            <th>role</th>
            <th>Họ và tên</th>
            <th>Số điện thoại</th>
            <th>Địa chỉ</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${users_list}" var = "user">
            <tr>
                <td>
                    <img style="width: 120px" src="${user.avatar}" alt="Avatar của ${user.firstname} ${user.lastname}"/>
                </td>
                <td>${user.userId}</td>
                <td>${user.roleId.roleId}</td>
                <td>${user.firstname} ${user.lastname}</td>
                <td>${user.phonenumber}</td>
                <td>${user.location}</td>

                                    <td>
                                        <a href="<c:url value="/admin/users/${user.userId}" />" class = "btn btn-success">Cập nhật</a>
                                        <button class = "btn btn-danger">Xóa nà</button>
                                    </td>

                <td>
                    <c:url value="/api/server/admin/users/${user.userId}" var="userPathAPI"/>
                    <a href="<c:url value="/admin/users/${user.userId}" />" class = "btn btn-success">Cập nhật</a>
                    <button class = "btn btn-danger" onclick="deleteUser('${userPathAPI}', ${user.userId})">Xóa nà</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>-->
