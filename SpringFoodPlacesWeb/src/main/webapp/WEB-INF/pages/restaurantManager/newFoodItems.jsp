<%-- 
    Document   : newFoodItems
    Created on : Aug 17, 2023, 11:51:46 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
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
<div class="container update-food">
<h1 style="text-align: center">CẬP NHẬT MÓN ĂN</h1>
    <c:url value="/restaurantManager/foodItems/newFoodItems" var="action"/>
    <form:form method="post" action="${action}" modelAttribute="foodItem" enctype="multipart/form-data">
        <form:errors path="*" element="div" cssClass="alert alert-danger" />
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="foodName" id="foodName" placeholder="Nhập tên món... " name="foodName" />
            <label for="foodName">Nhập tên món...</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="price" id="price" placeholder="Nhập giá... " name="price" />
            <label for="price">Nhập giá...</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="description" id="description" placeholder="Nhập loại... " name="description" />
            <label for="description">Nhập loại...</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:select class="form-select" id="shelfLife_list" name="shelfLife_list" path="shelflifeId">
                <c:forEach items="${shelfLife_list}" var="sL">
                    <c:choose>
                        <c:when test="${sL.shelflifeId == foodItem.shelflifeId.shelflifeId}">
                            <option value="${sL.shelflifeId}" selected="${sL.shelflifeId}">${sL.shelflifeName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${sL.shelflifeId}">${sL.shelflifeName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>

            <label for="restaurants" class="form-label">Danh mục thời gian bán</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:select class="form-select" id="category_list" name="category_list" path="categoryfoodId">
                <c:forEach items="${category_list}" var="cate">
                    <c:choose>
                        <c:when test="${cate.categoryfoodId == foodItem.categoryfoodId.categoryfoodId}">
                            <option value="${cate.categoryfoodId}" selected="${cate.categoryfoodId}">${cate.categoryname}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${cate.categoryfoodId}">${cate.categoryname}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>

            <label for="restaurants" class="form-label">Danh mục thức ăn</label>
        </div>



        <div class="form-floating mb-3 mt-3">
            <form:input type="file" class="form-control" path="file" id="file" name="file" />
            <label for="file">Avatar</label>
        </div>

        <button type="submit" class="btn btn-info">
            <c:choose>
                <c:when test="${foodItem.foodId == null}">
                    Thêm món
                </c:when>
                <c:otherwise>
                    Cập nhật món
                </c:otherwise>
            </c:choose>
        </button>
        <form:hidden path="foodId" />
        <form:hidden path="restaurantId" />
        <form:hidden path="avatar" />
        <form:hidden path="active" />
    </form:form>
</div>
