<%-- 
    Document   : categories
    Created on : Aug 9, 2023, 9:11:49 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/foodItems.css" /> "/>
<c:url value="/restaurantManager/indexCategories" var = "action" />

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

    <!--    <div>
    <c:url value="categoriesFood/newCategoriesFood" var="newCateAction">
        <c:param name="restaurantId" value="${param.restaurantId}"></c:param>
    </c:url>
    <a href="${newCateAction}" class = "btn btn-success"> Thêm Danh Mục </a>
</div>-->

    <div>
        <h1 class="heading">DANH MỤC MÓN ĂN</h1>
        <form:form method="post" modelAttribute="cate">
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="categoryname" id="categoryname" placeholder="Nhập tên danh mục... " name="categoryname" />
                <label for="categoryname">Nhập Tên Danh Mục...</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <button class="btn btn-info" type="submit">
                    Thêm danh mục PROMAX
                </button>
            </div>
        </form:form>
    </div>

    
</section>
<hr>
<section class="container newfood-container">
    <table class="table-hover">
        <thead>
            <tr>
                <th>Category Id</th>
                <th>Category Name</th>
                <th>id nha hang</th>
        
        </tr>
        </thead>

        <tbody>
            <c:forEach items="${categories}" var="c">

                <tr>
                    <td>${c.categoryfoodId}</td>
                    <td>${c.categoryname}</td>
                    <td>${c.restaurantId}</td>
                    <td>
                        <a href="<c:url value="/restaurantManager/categoriesFood/newCategoriesFood/${c.categoryfoodId}" />" class = "btn btn-success">Cập nhật</a>
                        <c:url value="/api/server/restaurantManager/categoriesFood/newCategoriesFood/${c.categoryfoodId}" var="apiDel" />
                        <button class = "btn btn-danger" onclick="delCate('${apiDel}', ${c.categoryfoodId})">Xóa</button>
                    </td>
                </tr>

            </c:forEach>
        </tbody> 
    </table>
</section>

<script src="<c:url value="/js/category.js" />"></script>

