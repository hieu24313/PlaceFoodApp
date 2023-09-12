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
<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">ĐĂNG KÝ NHÀ HÀNG MỚI</h1>
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
<hr class="container" />
<div class="body">
    <div class="body body__left">
        <div class="container">
            <c:url value="/admin/restaurants/newRestaurant" var="action" />
            <form:form
                method="post"
                action="${action}"
                modelAttribute="restaurant"
                enctype="multipart/form-data"
                >
                <form:errors path="*" element="div" cssClass="alert alert-danger" />
                <div class="form-floating mb-3 mt-3">
                    <form:input
                        type="text"
                        class="form-control"
                        path="restaurantName"
                        id="restaurantName"
                        placeholder="Nhập tên nhà hàng... "
                        name="restaurantName"
                        />
                    <label for="restaurantName">Nhập tên nhà hàng...</label>
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:input
                        type="text"
                        class="form-control"
                        path="location"
                        id="location"
                        placeholder="Nhập địa chỉ nhà hàng... "
                        name="location"
                        />
                    <label for="location">Nhập địa chỉ nhà hàng...</label>
                </div>

<!--                <div class="form-floating mb-3 mt-3">
                    <form:input
                        type="text"
                        class="form-control"
                        path="mapLink"
                        id="mapLink"
                        placeholder="Nhập tên nhà hàng... "
                        name="mapLink"
                        />
                    <label for="restaurantName"
                           >Cái maplink này nhập đại đi chứ đách xử lý đc...</label
                    >
                </div>-->

                <label for="file" class="drop-container" id="dropcontainer">
                    <span class="drop-title">Drop your avatar here</span>
                    or
                    <form:input type="file" class="form-control" path="file" id="file" name="file" />
                    <!--<input type="file" id="file" accept="image/*" required>-->
                </label>

                <div class="form-floating mb-3 mt-3">
                    <form:select
                        class="form-select"
                        id="restaurants"
                        name="restaurants"
                        path="confirmationStatus"
                        >
<!--                        <option value="true" selected="true">True</option>
                        <option value="false" selected="false">False</option>-->
                        <!--MÊT :))) -->
                        <c:choose>
                            <c:when test="${true == restaurant.confirmationStatus}">
                                <option
                                    value="${true}"
                                    selected="${true}"
                                    >
                                    True
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="true">
                                    True
                                </option>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${false == restaurant.confirmationStatus}">
                                <option
                                    value="${false}"
                                    selected="${false}"
                                    >
                                    False
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="false">
                                    False
                                </option>
                            </c:otherwise>
                        </c:choose>

                    </form:select>

                    <label for="restaurants" class="form-label">Xác nhận nhà hàng hoạt động</label>
                </div>

                <div class="form-floating mb-3 mt-3" style="display: flex">

                    <div>
                        <!-- <form:input type="text" class="form-control" path="userId" id="search_userId" name="search_userId"  /> -->
                        <input
                            type="text"
                            class="form-control"
                            id="search_userId"
                            name="search_userId"
                            value="${restaurant.userId.firstname} ${restaurant.userId.lastname}"
                            />
                        <select class="form-select" onchange="getid()" id="suggest"></select>
                    </div>

                    <div class="form-floating mb-3 mt-3">
                        <form:input path="userId" class="getId" id="load_userId_js" />
                    </div>

                    <!--                    <div class="form-floating mb-3 mt-3">
                    <form:input path="userId" class="getId" id="" />
                </div>-->
                </div>

                <div class="form-floating mb-3 mt-3">
                    <form:select
                        class="form-select"
                        id="restaurants"
                        name="restaurants"
                        path="restaurantStatus"
                        >
                        <c:forEach items="${restaurantStatus_list}" var="rS">
                            <c:choose>
                                <c:when test="${rS.statusId == restaurant.restaurantStatus.statusId}">
                                    <option
                                        value="${rS.statusId}"
                                        selected="${rS.restaurantStatus}"
                                        >
                                        ${rS.restaurantStatus}
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${rS.statusId}">
                                        ${rS.restaurantStatus}
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>

                    <label for="restaurants" class="form-label"
                           >Danh mục restaurantStatus</label
                    >
                </div>
                <button type="submit">
                    <c:choose>
                        <c:when test="${restaurant.restaurantId == null}">
                            Thêm nhà hàng
                        </c:when>
                        <c:otherwise> Cập nhật nhà hàng 
                            <form:hidden path="restaurantId" />
                            <form:hidden path="avatar" />
                            <form:hidden path="active" />
                            <form:hidden path="confirmationStatus" />
                            <form:hidden path="mapLink" />
                        </c:otherwise>
                    </c:choose>
                </button>
            </form:form>
        </div>
    </div>

    <div id="googleMap" class="body body__right">
        <div class="map-container">
            <iframe
                src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d251637.95196238213!2d105.6189045!3d9.779349!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1693067788859!5m2!1svi!2s"
                width="600"
                height="450"
                style="border: 0"
                allowfullscreen=""
                loading="lazy"
                referrerpolicy="no-referrer-when-downgrade"
                ></iframe>
            <label for="googleMap"
                   >Bản đồ vị trí nhà hàng</label
            >
        </div>
    </div>
</div>

<!--<div class="container">
<c:url value="/admin/restaurants/newRestaurant" var="action"/>
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
    <form:select class="form-select" id="restaurants" name="restaurants" path="confirmationStatus">
        <option value="true" selected="true">True</option>
        <option value="false" selected="false">False</option>
    </form:select>

    <label for="restaurants" class="form-label">Confirm?</label>
</div>


<div class="form-floating mb-3 mt-3" style="display: flex">
                <div>
    <form:select class="form-select" id="restaurants" name="restaurants" path="userId">
        <c:forEach items="${user_list}" var="user">
            <c:choose>
                <c:when test="${user.userId == restaurant.userId.userId}">
                    <option value="${user.userId}" selected="${user.userId}">${user.userId} - ${user.lastname}</option>
                </c:when>
                <c:otherwise>
                    <option value="${user.userId}">${user.userId} - ${user.lastname}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </form:select>

    <label for="restaurants" class="form-label">Danh mục Users</label>
</div>

    <div>
    <form:input type="text" class="form-control" path="userId" id="search_userId" name="search_userId"  /> 
   <input type="text" class="form-control" id="search_userId" name="search_userId" />
   <select id="suggest">

   </select>
</div>

<div class="form-floating mb-3 mt-3">
    <form:input path="userId" id="load_userId_js" />

</div>
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