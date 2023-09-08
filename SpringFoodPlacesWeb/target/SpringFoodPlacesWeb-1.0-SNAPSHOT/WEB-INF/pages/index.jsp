<%-- 
    Document   : index
    Created on : Jul 27, 2023, 10:33:58 AM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href=" <c:url value="/css/index.css" /> "/>
<c:url value="/" var = "action" />


<h1 style = "text-align: center">${msg}</h1>

<c:if test="${param.accessDenied != null}">
    <div class="alert alert-danger alert-dismissible fade show">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <strong>Danger!</strong> Không có quyền
    </div>
</c:if>

<section class = "body">
    <h1 class ="text-center text-white">CHƯA CÓ GIAO DIỆN</h1>
<!--    <img style="width: 500px; height: 400px;" src="https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-6/284548383_3316239618699746_7956919528871930159_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5614bc&_nc_ohc=Y1hQGVreJA4AX9iQCKd&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfBwNqEC57ws0mt8cQdCWYvFPI294ENPfpm0crelN7VJHg&oe=64FAA55F" alt="alt"/>
    <img style="width: 500px; height: 400px;" src="https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-6/284548383_3316239618699746_7956919528871930159_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5614bc&_nc_ohc=Y1hQGVreJA4AX9iQCKd&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfBwNqEC57ws0mt8cQdCWYvFPI294ENPfpm0crelN7VJHg&oe=64FAA55F" alt="alt"/>
    <img style="width: 500px; height: 400px;" src="https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-6/284548383_3316239618699746_7956919528871930159_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5614bc&_nc_ohc=Y1hQGVreJA4AX9iQCKd&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfBwNqEC57ws0mt8cQdCWYvFPI294ENPfpm0crelN7VJHg&oe=64FAA55F" alt="alt"/>-->
</section>



    
