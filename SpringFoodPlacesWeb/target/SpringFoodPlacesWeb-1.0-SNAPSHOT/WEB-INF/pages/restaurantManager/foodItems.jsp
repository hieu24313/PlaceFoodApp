<%-- 
    Document   : foodItems
    Created on : Aug 17, 2023, 10:19:35 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/foodItems.css" /> "/>
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
<section class="container food-container">
    <div>
        <form:form method="post" action="${action}" modelAttribute="foodItem" enctype="multipart/form-data">
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
            <h1 class="heading">CẬP NHẬT MÓN ĂN MỚI</h1>
                
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

            <div class="form-floating mb-3 mt-3">
                <button class="btn btn-info" type="submit">
                    Thêm món ăn PROMAX
                </button>
            </div>
        </form:form>
    </div>

<!--    <div>
        <a href="foodItems/newFoodItems" class = "btn btn-success mt-3"> Thêm món ăn </a>
    </div>-->

   
</section>
 <hr>
<section class="container newfood-container">
     <table class="table-hover ">
        <thead>
            <tr>
                <th>Id</th>
                <th>Ảnh</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Tình Trạng</th>
                <th>mô tả</th>

       
        </tr>
        </thead>

        <tbody>
            <c:forEach items="${foodItems}" var="food">

                <tr>
                    <td>${food.foodId}</td>
                    <td><img style="width:120px" src=${food.avatar} /></td>
                    <td>${food.foodName}</td>
                    <td>${food.price}</td>
                    <td>${food.available}</td>
                    <td>${food.description}</td>

                    <td>
                        <c:url value="/restaurantManager/foodItems/${food.foodId}" var="addFoodItemAction">
                            <c:param name="restaurantId" value="${param.restaurantId}"></c:param>
                        </c:url>
                        <a class = "btn btn-success" href="${addFoodItemAction}" />Cập nhật</a>

                        <c:url value="/api/server/restaurantManager/foodItems/${food.foodId}/" var="apiDel" />
                        <button class = "btn btn-danger" onclick="deleteFood('${apiDel}', ${food.foodId})">Xóa</button>
                    </td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</section>
 
 <section class="page-size">
    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <c:url value="/restaurantManager/foodItems?restaurantId=${param.get('restaurantId')}" var="pageAction">
                <c:param name="pageAll"></c:param>
            </c:url>
            <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả món ăn</a></li>

            <c:forEach begin="1" end="${counter}" var = "i">
                <c:url value="/restaurantManager/foodItems?restaurantId=${param.get('restaurantId')}" var="pageAction">
                    <c:param name="page" value="${i}"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>
</section>

<!--<section>
    <div class = "page-size">

            <ul class="">
                <c:url value="/restaurantManager/restaurants" var="pageAction">
                    <c:param name="pageAll"></c:param>
                </c:url>

                <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả nhà hàng</a></li>

                <c:choose>
                    <c:when test="${param.confirm eq 'false'}">
                        <c:forEach begin="1" end="${counter}" var = "i">
                            <c:url value="/restaurantManager/restaurants" var="pageAction">
                                <c:param name="confirm" value="false" />
                                <c:param name="page" value="${i}"></c:param>

                            </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:when>

                    <c:when test="${param.confirm eq 'true'}">
                        <c:forEach begin="1" end="${counter}" var = "i">
                            <c:url value="/restaurantManager/restaurants" var="pageAction">
                                <c:param name="confirm" value="true" />
                                <c:param name="page" value="${i}"></c:param>

                            </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="${counter}" var = "i">
                                <c:url value="/restaurantManager/restaurants" var="pageAction">
                                    <c:param name="page" value="${i}"></c:param>
                                </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </div>
</section>-->

<script src="<c:url value="/js/foodItems.js" />"></script>
