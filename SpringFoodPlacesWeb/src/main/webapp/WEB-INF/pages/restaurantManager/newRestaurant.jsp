<%-- 
    Document   : newRestaurant
    Created on : Aug 6, 2023, 4:19:27 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/newRestaurant.css" /> "/>
<script src="<c:url value="/js/restaurants.js" />"></script>

<form:form modelAttribute="restaurant">
    <c:choose>
        <c:when test="${restaurant.restaurantId != null && restaurant.confirmationStatus == true}">
            <div class="infor">
                <div class="profile-cards">
                    <div class="card">
                        <div class="card-description">
                            <h2 style="text-transform: uppercase; font-size: 40px" class="card-description-title">NHÀ HÀNG: ${restaurant.restaurantName}</h2>

                            <span style="text-transform: uppercase;" class="card-description-profession">CHỦ NHÀ HÀNG: ${restaurant.userId.firstname} ${restaurant.userId.lastname}</span>
                            <span style="text-transform: uppercase;" class="card-description-profession">Địa chỉ: ${restaurant.userId.location}</span>
                        </div>

                        <div class="avatar-restaurant">
                            <img
                                src="${restaurant.avatar}"
                                class="card-image"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>
</form:form>


<form:form modelAttribute="restaurant" action="${actionCategoryFood}" method="get">
    <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="actionCategoryFood"/>
    <c:choose>
        <c:when test="${restaurant.restaurantId != null && restaurant.confirmationStatus == true}">
            <h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">CÁC MÓN ĂN</h1>
            <hr class="container">

            <div class="food-home">
                <div class="food-home__top">
                    
<!--                    <div>
                        <c:url value="/restaurantManager/categoriesFood" var="editCategoriesFoodAction">
                            <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                        </c:url>
                        <a href="${editCategoriesFoodAction}">
                                Quản lý danh mục                                
                        </a>
                    </div>-->
                    <div class="list-categories">
                        <div>
                            <c:url value="/restaurantManager/categoriesFood" var="editCategoriesFoodAction">
                                <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                            </c:url>
                            <a href="${editCategoriesFoodAction}">
                                <h3>Quản lý danh mục</h3>
                            </a>
                        </div>

                        <div>
                            <c:url value="/restaurantManager/shelfLife" var="editShelfLifeAction">
                                <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                            </c:url>
                            <a href="${editShelfLifeAction}">
                                <h3>Quản lý thời gian bán</h3>
                            </a>
                        </div>

                        <div>
                            <c:url value="/restaurantManager/foodItems" var="editFoodAction">
                                <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                            </c:url>
                            <a href="${editFoodAction}">
                                <h3>Quản lý món ăn</h3>
                            </a>
                        </div>
                    </div>

                    <ul class="category">
                        <li>
                            <button>
                                <!--<a href="${restaurant.restaurantId}">Toàn bộ món ăn</a>-->
                                <a href="${restaurant.restaurantId}" onclick="delayScrollToClickedPosition(this)">Toàn bộ món ăn</a>

                            </button>
                        </li>
                        <c:forEach items="${category_list}" var="cate">
                            <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="actionCategoryFood">
                                <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                                <c:param name="cateFoodId" value="${cate.categoryfoodId}"></c:param>
                            </c:url>
                            <li>
                                <button>
                                    <a href="${actionCategoryFood}">${cate.categoryname}</a>
                                </button>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <section>
                    <c:if test="${counter > 1}">
                        <ul class="pagination mt-1">
                            <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="pageAction">
                                <c:param name="pageAll"></c:param>
                            </c:url>
                            <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả user</a></li>

                            <c:forEach begin="1" end="${counter}" var = "i">
                                <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="pageAction">
                                    <c:param name="page" value="${i}"></c:param>
                                </c:url>
                                <li class="page-item"><a class="page-link" href="${pageAction}">${i}</a></li>
                                </c:forEach>
                        </ul>
                    </c:if>
                </section>

                <div class="food-home__bottom">

                    <section style="background-color: #eee;">
                        <div class="container py-5">
                            <h4 class="text-center mb-5"><strong>Product listing</strong></h4>

                            <div class="row">
                                <c:forEach items="${food_list}" var="food">
                                    <div class="col-lg-3 col-md-12 mb-4 food-item">
                                        <div class="my-img-link bg-image hover-zoom ripple shadow-1-strong rounded">
                                            <c:url value="/restaurantManager/foodItems/${food.foodId}" var="addFoodItemAction">
                                                <c:param name="restaurantId" value="${restaurant.restaurantId}"></c:param>
                                            </c:url>
                                            <a href="${addFoodItemAction}" />
                                            <!--<a href="<c:url value="/restaurantManager/foodItems/${food.foodId}" />">-->
                                            
                                                <img src="${food.avatar}"
                                                 class="w-100" />

                                            <div class="mask" >
                                                
                                                    <h5>Tên món: ${food.foodName}</h5>
                                                    <!--nhu-->
<!--                                                    <h5>Giá: ${food.price}</h5>
                                                    <h5>Danh mục món: ${food.categoryfoodId.categoryname}</h5>
                                                    <h5>Mô tả: ${food.description}</h5>
                                                    <h5>Nhà hàng: ${food.restaurantId.restaurantName} - ${food.restaurantId.restaurantId}</h5>-->
                                            </div>
                                                
                                                <!--nhu-->
<!--                                            <div class="hover-overlay">
                                                <div class="mask" style="background-color: rgba(253, 253, 253, 0.15);"></div>
                                            </div>-->
                                            </a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </section>

                    <ul class="food-list">
                        <c:forEach items="${food_list}" var="food">
                            <li>
                                ${ food.foodName}
                            </li>
                        </c:forEach>
                    </ul>
                    
                </div>
            </div>
        </c:when>
    </c:choose>
</form:form>

<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">ĐĂNG KÝ NHÀ HÀNG MỚI</h1>
<hr class="container">
<div class="body">
    <div class="body body__left">
        <div class="container">
            <c:url value="/restaurantManager/restaurants/newRestaurant" var="action"/>
            <form:form method="post" action="${action}" modelAttribute="restaurant" enctype="multipart/form-data">
                <form:errors path="*" element="div" cssClass="alert alert-danger" />

                <div class="form-floating mb-3 mt-3">
                    <form:input type="text" class="form-control" path="restaurantName" id="restaurantName" placeholder="Nhập tên nhà hàng... " name="restaurantName" />
                    <label for="restaurantName">Nhập tên nhà hàng...</label>
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:input type="text" class="form-control" path="location" id="location" placeholder="Nhập địa chỉ nhà hàng... " name="location" />
                    <label for="location">Nhập địa chỉ nhà hàng...</label>
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:input type="text" class="form-control" path="mapLink" id="mapLink" placeholder="Nhập tên nhà hàng... " name="mapLink" />
                    <label for="restaurantName">Cái maplink này nhập đại đi chứ đách xử lý đc...</label>
                </div>

                <label for="file" class="drop-container" id="dropcontainer">
                    <span class="drop-title">Drop your avatar here</span>
                    or
                    <form:input type="file" class="form-control" path="file" id="file" name="file" />
                    <!--<input type="file" id="file" accept="image/*" required>-->
                </label>

                <div class="form-floating mb-3 mt-3">
                    <form:select class="form-select" id="restaurants" name="restaurants" path="restaurantStatus">
                        <c:forEach items="${restaurantStatus_list}" var="rS">
                            <c:choose>
                                <c:when test="${rS.statusId == restaurant.restaurantStatus.statusId}">
                                    <option value="${rS.statusId}" selected="${rS.restaurantStatus}">${rS.restaurantStatus}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${rS.statusId}">${rS.restaurantStatus}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>

                    <label for="restaurants" class="form-label">Danh mục restaurantStatus</label>
                </div>


                <button type="submit">
                    <c:choose>
                        <c:when test="${restaurant.restaurantId == null}">
                            Thêm nhà hàng
                        </c:when>
                        <c:otherwise>
                            Cập nhật nhà hàng
                        </c:otherwise>
                    </c:choose>
                </button>
                <form:hidden path="restaurantId" />
                <form:hidden path="avatar" />
                <form:hidden path="confirmationStatus" />
                <form:hidden path="active" />
            </form:form>
        </div>
    </div>

    <div id="googleMap" class="body body__right">
        <div class="map-container">
            <iframe src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d251637.95196238213!2d105.6189045!3d9.779349!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1693067788859!5m2!1svi!2s" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            <label for="googleMap">Bản đồ này để cho đẹp thôi chứ đách phải API Google Map đâu :)</label>
        </div>
    </div>

</div>

<!--<div class="container">
<c:url value="/restaurantManager/restaurants/newRestaurant" var="action"/>
<form:form method="post" action="${action}" modelAttribute="restaurant" enctype="multipart/form-data">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <div class="form-floating mb-3 mt-3">
    <form:input type="text" class="form-control" path="restaurantName" id="restaurantName" placeholder="Nhập tên nhà hàng... " name="restaurantName" />
    <label for="restaurantName">Nhập tên nhà hàng...</label>
</div>

<div class="form-floating mb-3 mt-3">
    <form:input type="text" class="form-control" path="location" id="location" placeholder="Nhập địa chỉ nhà hàng... " name="location" />
    <label for="location">Nhập địa chỉ nhà hàng...</label>
</div>

<div class="form-floating mb-3 mt-3">
    <form:input type="text" class="form-control" path="mapLink" id="mapLink" placeholder="Nhập tên nhà hàng... " name="mapLink" />
    <label for="restaurantName">Cái maplink này nhập đại đi chứ đách xử lý đc...</label>
</div>

<div class="form-floating mb-3 mt-3">
    <form:input type="file" class="form-control" path="file" id="file" name="file" />
    <label for="file">Avatar</label>
</div>

<div class="form-floating mb-3 mt-3">
    <form:select class="form-select" id="restaurants" name="restaurants" path="restaurantStatus">
        <c:forEach items="${restaurantStatus_list}" var="rS">
            <c:choose>
                <c:when test="${rS.statusId == restaurant.restaurantStatus.statusId}">
                    <option value="${rS.statusId}" selected="${rS.restaurantStatus}">${rS.restaurantStatus}</option>
                </c:when>
                <c:otherwise>
                    <option value="${rS.statusId}">${rS.restaurantStatus}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </form:select>

    <label for="restaurants" class="form-label">Danh mục restaurantStatus</label>
</div>
<button type="submit" class="btn btn-info">
    <c:choose>
        <c:when test="${restaurant.restaurantId == null}">
            Thêm nhà hàng
        </c:when>
        <c:otherwise>
            Cập nhật nhà hàng
        </c:otherwise>
    </c:choose>
</button>
    <form:hidden path="restaurantId" />
    <form:hidden path="avatar" />
</form:form>
</div>-->