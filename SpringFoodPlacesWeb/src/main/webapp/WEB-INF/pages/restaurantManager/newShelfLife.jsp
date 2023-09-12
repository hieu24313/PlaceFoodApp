<%-- 
    Document   : categories
    Created on : Aug 9, 2023, 9:11:49 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
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
<div class="container update-food">

    <c:url value="/restaurantManager/shelfLife/newShelfLife" var="action"/>


    <form:form method="post" action="${action}" modelAttribute="shelfLife">
        <form:hidden path="shelflifeId" />
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" path="shelflifeName" id="shelflifeName" placeholder="Nhập Tên... " name="shelflifeName" />
        <label for="shelflifeName">Nhập Tên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="date" class="form-control" path="fromDate" id="fromDate" placeholder="Nhập Ngày Bắt Đầu... " name="fromDate" />
        <label for="fromDate">Nhập Ngày Bắt Đầu</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="date" class="form-control" path="toDate" id="toDate" placeholder="Nhập Ngày Kết Thúc... " name="toDate" />
        <label for="toDate">Nhập Ngày Kết Thúc</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-info" type="submit">
            <c:choose>
                <c:when test="${shelfLife.shelflifeId == null}">Thêm thời gian bán</c:when>
                <c:otherwise>
                    <form:hidden path="restaurantId" />
                    <form:hidden path="active" />
                    Cập nhật thời gian bán
                </c:otherwise>
            </c:choose>
        </button>
    </div>
</form:form>
</div>