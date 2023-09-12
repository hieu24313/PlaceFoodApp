<%-- 
    Document   : newcategory
    Created on : Aug 9, 2023, 11:22:59 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="container update-food">
<h1 style="text-align: center">Thêm Danh Mục</h1>
    <c:url value="/restaurantManager/categoriesFood/newCategoriesFood" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="cate">
        <form:hidden path="categoryfoodId" />
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="categoryname" id="categoryname" placeholder="Nhập tên danh mục... " name="categoryname" />
            <label for="categoryname">Nhập Tên Danh Mục...</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-info" type="submit">
                <c:choose>
                    <c:when test="${cate.categoryfoodId == null}">Thêm danh mục</c:when>
                    <c:otherwise>
                        <form:hidden path="restaurantId" />
                        <form:hidden path="active" />
                        Cập nhật danh mục
                    </c:otherwise>
                </c:choose>
            </button>
        </div>

    </form:form>
</div>
