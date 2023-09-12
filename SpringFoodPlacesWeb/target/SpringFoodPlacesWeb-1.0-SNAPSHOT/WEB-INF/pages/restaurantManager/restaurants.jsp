<%-- 
    Document   : restaurants
    Created on : Aug 2, 2023, 9:49:37 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="<c:url value="/js/restaurants.js" />"></script>
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/restaurant.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>

<c:url value="/restaurantManager/restaurants" var = "action" />
<!--<h1 id="bugs" class="animate__backInDown" style = "transition: 5s; text-align: center; display: none">${param.msg}</h1>-->

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

<!--<script>
    window.onload = () => {
        let bugs = document.getElementById("bugs");
        console.log(bugs.innerText);
        if (bugs.innerText !== null) {
            console.log(bugs.innerText);
            bugs.style.display="block";
        }
    }
</script>-->

<section>
    <c:if test="${counter > 1}">
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
    </c:if>

    <c:if test="${counter <= 1}">
        <div class = "page-size">
            <ul class="">
                <c:url value="/restaurantManager/restaurants" var="pageAction">
                    <c:param name="pageAll"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageAction}">Tất cả restaurants</a></li>   
            </ul>
        </div>
    </c:if>

    <div class = "page-size">
        <ul class="">
            <c:url value="/restaurantManager/restaurants" var="pageAction">
                <c:param name="pageAll"></c:param>
            </c:url>
            <li class="page-item"><a href="restaurants/newRestaurant" class = "btn btn-success"> ĐĂNG KÝ NHÀ HÀNG </a></li>  
            <li class="page-item"><a class = "btn btn-success" href="${action}">TOÀN BỘ</a></li>   
                <c:url value="/restaurantManager/restaurants" var="confirmAction">
                    <c:param name="confirm" value="true"></c:param>
                </c:url>
            <li class="page-item"><a class = "btn btn-success" href="${confirmAction}">ĐÃ DUYỆT</a></li> 
                <c:url value="/restaurantManager/restaurants" var="confirmAction">
                    <c:param name="confirm" value="false"></c:param>
                </c:url>
            <li class="page-item"><a class = "btn btn-success" href="${confirmAction}">CHƯA DUYỆT</a></li> 
        </ul>
    </div>

    <!--    <div>
            <a href="restaurants/newRestaurant" class = "btn btn-success"> Thêm restaurants </a>
            <a class = "btn btn-success" href="${action}">Toàn bộ</a>
    <c:url value="/restaurantManager/restaurants" var="confirmAction">
        <c:param name="confirm" value="true"></c:param>
    </c:url>
    <a class = "btn btn-success" href="${confirmAction}">ĐÃ DUYỆT</a>

    <c:url value="/restaurantManager/restaurants" var="confirmAction">
        <c:param name="confirm" value="false"></c:param>
    </c:url>
    <a class = "btn btn-success" href="${confirmAction}">CHƯA DUYỆT</a>
</div>-->
</section>

<section>
    <div class="row mx-auto">
        <c:forEach items="${restaurant_list}" var = "restaurant">
            <div class="col-lg-4 col-md-6 col-sm-12">

                <div class="course_card">
                    <a class ="detail-restaurant" href="<c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" />">
                        <div class="course_card_img">
                            <img ,="" alt="Ảnh của ${restaurant.restaurantName}" src="${restaurant.avatar}" />
                        </div>
                        <div class="course_card_content">
                            <h3 class="title">
                                <strong>Nhà hàng: ${restaurant.restaurantName} (${restaurant.restaurantId})</strong>
                            </h3>

                            <h5 class="title">
                                Địa chỉ: ${restaurant.location}
                            </h5>

                            <h5 class="title">
                                Chủ nhà hàng: ${restaurant.userId.firstname} ${restaurant.userId.lastname}
                            </h5>

                            <p class="description">

                            </p>

                            <c:choose>
                                <c:when test="${restaurant.confirmationStatus eq 'true'}">
                                    <div class="button-confirm-true">Đã xác nhận</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="button-confirm-false">Chưa xác nhận</div>
                                </c:otherwise>
                            </c:choose>

                            <div class="button-restaurant-status">${restaurant.restaurantStatus.restaurantStatus}</div>
                        </div>
                    </a>
                    <div class="course_card_footer">
                        <!--<a class="nav-item" href="<c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" />">Xem chi tiết</a>-->
                        <c:url value="/api/server/restaurantManager/restaurants/${restaurant.restaurantId}" var="restaurantPathAPI"/>

                        <a class="nav-item delete-button" onclick="deleteRestaurant('${restaurantPathAPI}', ${restaurant.restaurantId})" href="javascript:;">Xóa nhà hàng</a>
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
            <th>Tên nhà hàng</th>
            <th>Địa chỉ</th>
            <th>Confirm-status</th>
            <th>Chủ nhà hàng</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
<c:forEach items="${restaurant_list}" var = "restaurant">
    <tr>
        <td>
            <img style="width: 120px" src="${restaurant.avatar}" alt="Avatar của ${restaurant.restaurantName} "/>
        </td>
        <td>${restaurant.restaurantId}</td>
        <td>${restaurant.restaurantName}</td>
        <td>${restaurant.location}</td>
        <td>${restaurant.confirmationStatus}</td>
        <td>${restaurant.userId.userId}</td>
        <td>${restaurant.restaurantStatus.restaurantStatus}</td>
        <td>
    <c:url value="/api/server/restaurantManager/restaurants/${restaurant.restaurantId}" var="restaurantPathAPI"/>
    <a href="<c:url value="/restaurantManager/restaurants/${restaurant.restaurantId}" />" class = "btn btn-success">Cập nhật</a>
    <button class = "btn btn-danger" onclick="deleteRestaurant('${restaurantPathAPI}', ${restaurant.restaurantId})">Xóa nà</button>
</td>
</tr>
</c:forEach>
</tbody>
</table>-->

