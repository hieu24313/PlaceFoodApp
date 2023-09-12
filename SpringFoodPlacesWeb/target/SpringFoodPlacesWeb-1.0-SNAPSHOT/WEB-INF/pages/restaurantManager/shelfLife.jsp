<%-- 
    Document   : shelfLife
    Created on : Aug 12, 2023, 11:24:59 AM
    Author     : HP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/foodItems.css" /> "/>

<c:url value="/restaurantManager/indexShelfLife" var = "action" />
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
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
<section class="container food-container">
    <!--    <div>
            <a href="shelfLife/newShelfLife" class = "btn btn-success"> Thêm Thời Gian Bán </a>
        </div>-->

    <div>
        <h1 class="heading">QUẢN LÝ THỜI GIAN BÁN</h1>
        <form:form method="post" modelAttribute="shelfLife">
        
        <div class="form-floating mb-3 mt-3">
            <form:input required="required" type="text" class="form-control" path="shelflifeName" id="shelflifeName" placeholder="Nhập Tên... " name="shelflifeName" />
            <label for="shelflifeName">Nhập Tên</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:input required="required" type="date" class="form-control" path="fromDate" id="fromDate" placeholder="Nhập Ngày Bắt Đầu... " name="fromDate" />
            <label for="fromDate">Nhập Ngày Bắt Đầu</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:input required="required" type="date" class="form-control" path="toDate" id="toDate" placeholder="Nhập Ngày Kết Thúc... " name="toDate" />
            <label for="toDate">Nhập Ngày Kết Thúc</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-info" type="submit">
                Thêm thời gian bán
            </button>
        </div>
    </form:form>
</div>
</section>
    <hr>

<section class="container newfood-container">
    <table class="table-hover ">
    <thead>
        <tr>
            <th>Tên thời gian bán</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>

    </tr>
    </thead>

    <c:forEach items="${shelfLifes_All}" var="s">
        <tbody>
            <tr>
                <td>${s.shelflifeName}</td>
                <td>${s.fromDate}</td>
                <td>${s.toDate}</td>

                <td>
                    <a href="<c:url value="/restaurantManager/shelfLife/newShelfLife/${s.shelflifeId}" />" class = "btn btn-success">Cập nhật</a>
                    <c:url value="/api/server/restaurantManager/shelfLife/newShelfLife/${s.shelflifeId}" var="apiDel" />
                    <button class = "btn btn-danger" onclick="deleteShelfLife('${apiDel}', ${s.shelflifeId})">Xóa</button>
                </td>
            </tr>
        </tbody>
    </c:forEach>
</table>
</section>
<script src="<c:url value="/js/shelfLife.js" />"></script>

