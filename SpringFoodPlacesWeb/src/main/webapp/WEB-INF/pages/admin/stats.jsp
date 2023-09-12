<%-- 
    Document   : stats
    Created on : Sep 10, 2023, 11:33:18 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href=" <c:url value="/css/foodItems.css" /> "/>
<c:url value="/restaurantManager/indexCategories" var = "action" />
<link rel="stylesheet" href=" <c:url value="/css/background.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/toastBug.css" /> "/>
<link rel="stylesheet" href=" <c:url value="/css/stats.css" /> "/>
<script src="<c:url value="/js/mychart.js" />"></script>
<script>
    let labels = [];
    let data = [];

    <c:forEach var="s" items="${statsRestaurant}">
    labels.push('${s[1]}');
    data.push(${s[2]});
    </c:forEach>

    window.onload = function () {
        drawRevenueChartRestaurant(labels, data);
       
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
<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">THỐNG KÊ NHÀ HÀNG</h1>
<section class="search container mt-5">
    <c:url value="/admin/stats" var="statsAction_date" />
    <!--Có ai hiểu tại sao bỏ cái form sida này ra là nó bị mất cái form bọc lại lúc render không?-->
    <form class="d-flex">

    </form>

    <form class = "d-flex" action="${statsAction_date}">
        <input required="" class="form-control me-2" type="date" name="fromDate" >
        <input required class="form-control me-2" type="date" name="toDate">
        <button class="btn btn-primary" type="submit">Tìm</button>
    </form>

    <c:url value="/admin/stats" var="statsAction_quarter" />
    <form class="d-flex" action="${statsAction_quarter}">
        <input required type="number" min="1" max="4" step="1" placeholder="Quý" id="quarter-input" name="quarter">
        <input required type="number" min="2000" max="3000" step="1" placeholder="Năm" id="quarter-year" name="quarter-year">
        <button class="btn btn-primary" type="submit">Tìm theo quý</button>
    </form >
</section>
<section class="my-stats">
    <div class="stats">
        <section>
            <!--<h1 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em">THỐNG KÊ</h1>-->
            <section class="container newfood-container">


                <table class="table-hover ">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Tên nhà hàng</th>
                            <th>Doanh thu</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${statsRestaurant}" var="statsRestaurant">

                            <tr>
                                <!--<td></td>-->
                                <td>${statsRestaurant[0]}</td>
                                <td>${statsRestaurant[1]}</td>
                                <td>${statsRestaurant[2]} VNĐ</td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>

            </section>
            <div class="draw-chart container">

                <canvas id="RevenueChartRestaurant">

                </canvas>
                <h3 style="text-align: center; color: #5a2c1e; font-weight: bold; margin: 0.5em" class="text-center">Sơ đồ thống kê theo doanh thu từng nhà hàng</h3>
            </div>

        </section>

        <hr class="container mt-5 mb-5">

    </div>
</section>