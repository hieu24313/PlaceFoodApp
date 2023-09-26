<%-- 
    Document   : newRestaurant
    Created on : Aug 6, 2023, 4:19:27 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/newRestaurant.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/stats.css" /> "/>
<script src="<c:url value="/js/restaurants.js" />"></script>
<script src="<c:url value="/js/mychart.js" />"></script>

<script>
    let labels = [];
    let data = [];

    <c:forEach var="s" items="${statsFood}">
    labels.push('${s[1]}');
    data.push(${s[2]});
    </c:forEach>

    let labels_1 = [];
    let data_1 = [];

    <c:forEach var="s" items="${statsFoodByCate}">
    labels_1.push('${s[1]}');
    data_1.push(${s[2]});
    </c:forEach>
    window.onload = function () {
        drawRevenueChart(labels, data);
        drawRevenueChartByCate(labels_1, data_1);
    }

</script>

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
                            <span style="text-transform: uppercase;" class="card-description-profession">Followers: ${followers}</span>
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
            <h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 1.5em">THỐNG KÊ</h1>
            <hr>
            <section class="search container mt-5">
                <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="statsAction_date" />
                <!--Có ai hiểu tại sao bỏ cái form sida này ra là nó bị mất cái form bọc lại lúc render không?-->
                <form class="d-flex">

                </form>

                <form class = "d-flex" action="${statsAction_date}">
                    <label for="fromDate" class="text-danger">Từ ngày</label>
                    <input required="" class="form-control me-2" type="date" name="fromDate" >
                    <label for="toDate" class="text-danger">Đến ngày</label>
                    <input required class="form-control me-2" type="date" name="toDate">
                    
                    <button class="btn btn-primary" type="submit">Tìm</button>
                </form>

                <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="statsAction_quarter" />
                <form class="d-flex" action="${statsAction_quarter}">
                    <input required type="number" min="1" max="4" step="1" placeholder="Quý" id="quarter-input" name="quarter">
                    <input required type="number" min="2000" max="3000" step="1" placeholder="Năm" id="quarter-year" name="quarter-year">
                    <button class="btn btn-primary" type="submit">Tìm theo quý</button>
                </form >
            </section>
            <section class="my-stats">
                <div class="stats">
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <section>
                        <!--<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">THỐNG KÊ</h1>-->
                        <section class="container newfood-container">


                            <table class="table-hover ">
                                <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>Tên món</th>
                                        <th>SL bán</th>
                                        <th>Doanh thu</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${statsFood}" var="statsFood">

                                        <tr>
                                            <!--<td></td>-->
                                            <td>${statsFood[0]}</td>
                                            <td>${statsFood[1]}</td>
                                            <td>${statsFood[3]}</td>
                                            <td>${statsFood[2]}</td>

                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>

                        </section>
                        <div class="draw-chart container">

                            <canvas id="RevenueChart">

                            </canvas>
                            <h3 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em" class="text-center">Sơ đồ thống kê theo doanh thu từng món ăn</h3>
                        </div>

                    </section>

                    <hr class="container mt-5 mb-5">

                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->


                    <section>
                        <!--<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">THỐNG KÊ</h1>-->
                        <section class="container newfood-container">
                            <table class="table-hover ">
                                <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>Tên danh mục</th>
                                        <th>Doanh thu</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${statsFoodByCate}" var="statsFoodByCate">

                                        <tr>
                                            <!--<td></td>-->
                                            <td>${statsFoodByCate[0]}</td>
                                            <td>${statsFoodByCate[1]}</td>
                                            <td>${statsFoodByCate[2]}</td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>

                        </section>
                        <div class="container">
                            <!--
                            <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="statsByCateAction_date" />
                            <form class="d-flex" action="${statsByCateAction_date}">
                                <input required="" class="form-control me-2" type="date" name="fromDate" >
                                <input required class="form-control me-2" type="date" name="toDate">
                                <button class="btn btn-primary" type="submit">Tìm</button>
        
                            </form>
        
                            <c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" var="statsByCateAction_quarter" />
                            <form class="d-flex" action="${statsByCateAction_quarter}">
                                <input required type="number" min="1" max="4" step="1" placeholder="Quý" id="quarter-input" name="quarter">
                                <input required type="number" min="2000" max="3000" step="1" placeholder="Năm" id="quarter-year" name="quarter-year">
                                <button class="btn btn-primary" type="submit">Tìm theo quý</button>
                            </form >-->

                            <div class="draw-chart container">
                                <canvas id="RevenueChartFoodByCate">

                                </canvas>
                            </div>

                            <h3 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em" class="text-center">Sơ đồ thống kê theo doanh thu từng danh mục</h3>
                        </div>

                    </section>

                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                    <!--=============================================================================================-->
                </div>
            </section>



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
                            <!--nhu-->
                            <h4 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em"><strong>Product listing</strong></h4>

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
                                                <h5 class="text-danger"><strong>Giá: ${food.price}</strong></h5><!--
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
                    <hr class="container">
                    <!--nhu-->
                    <h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">CÁC ĐƠN HÀNG</h1>


                    <section class="container newfood-container">
                        <table class="table-hover ">
                            <thead>
                                <tr>
                                    <th>Mã hóa đơn</th>
                                    <th>Tên món</th>
                                    <th>Giá 1 món</th>
                                    <th>Số lượng</th>
                                    <th>Giá tổng</th>
                                    <th>Ngày tạo</th>
                                    <th>Địa chỉ giao</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach items="${receiptDetailPerfect_list}" var="receipts">

                                    <tr>
                                        <!--<td></td>-->
                                        <td>${receipts.receiptId}</td>
                                        <td>${receipts.foodName}</td>
                                        <td>${receipts.price}</td>
                                        <td>${receipts.quantity}</td>
                                        <td>${receipts.amount}</td>
                                        <td>${receipts.createdDate}</td>
                                        <td>${receipts.location}</td>
                                        
                                        <c:choose >
                                            <c:when test="${receipts.statusReceipt eq 'Chưa xác nhận'}">
                                                <td class="text-danger">${receipts.statusReceipt}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="text-success">${receipts.statusReceipt}</td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </section>

                </div>
            </div>
        </c:when>
    </c:choose>
</form:form>
<hr class="container">

<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">ĐĂNG KÝ NHÀ HÀNG MỚI</h1>
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

                <!--                <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="mapLink" id="mapLink" placeholder="Nhập tên nhà hàng... " name="mapLink" />
                <label for="restaurantName">Cái maplink này nhập đại đi chứ đách xử lý đc...</label>
            </div>-->

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
                <form:hidden path="mapLink" />
            </form:form>
        </div>
    </div>

    <div id="googleMap" class="body body__right">
        <div class="map-container">
            <iframe src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d251637.95196238213!2d105.6189045!3d9.779349!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1693067788859!5m2!1svi!2s" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            <label for="googleMap">Bản đồ vị trí nhà hàng</label>
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