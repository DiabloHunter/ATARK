<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<html>
<head>
    <title><fmt:message key="productDetails.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1><fmt:message key="productDetails.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
</div>
<div class = "inner_div">

    <form name="productForm" method="get" action="project" >
        <input type="hidden" name="command" value="showProductDetails" />
        <input type="hidden" name="id" value="${product.id}" />
        <input type="hidden" name="reserved" value="${product.reservedQuantity}" />
        <h4><fmt:message key="productDetails.code" bundle="${legend}"/></h4>
        <i><p>${product.code}</p></i></b><br/>
        <h4><fmt:message key="productDetails.nameRu" bundle="${legend}"/></h4>
        <i><p>${product.nameRu}</p></i></b><br/>
        <h4><fmt:message key="productDetails.nameEn" bundle="${legend}"/></h4>
        <i><p>${product.nameEn}</p></i></b><br/>
        <h4><fmt:message key="productDetails.descriptionRu" bundle="${legend}"/></h4>
        <i><p>${product.descriptionRu}</p></i></b><br/>
        <h4><fmt:message key="productDetails.descriptionEn" bundle="${legend}"/></h4>
        <i><p>${product.descriptionEn}</p></i></b><br/>
        <h4><fmt:message key="productDetails.cost" bundle="${legend}"/></h4>
        <i><p>${product.cost}</p></i></b><br/>
        <c:if test="${product.available}">
            <input type="checkbox" name="isAvailable" checked disabled/>
            <b><fmt:message key="productDetails.inStock" bundle="${legend}"/></b>
            <br/>
            <h4><fmt:message key="productDetails.quantity" bundle="${legend}"/></h4>
            <i><p>${product.quantity} ${product.uomEn}</p></i></b><br/>
        </c:if>
        <c:if test="${!product.available}">
            <input type="checkbox" name="isAvailable" disabled/>
            <b><fmt:message key="productDetails.inStock" bundle="${legend}"/></b>
            <br/>
            <h4><fmt:message key="productDetails.quantity" bundle="${legend}"/></h4>
            <i><p>0 ${product.uomEn}</p></i></b><br/>
        </c:if>
        <h4><fmt:message key="productDetails.notesRu" bundle="${legend}"/></h4>
        <i><p>${product.notesRu}</p></i></b><br/>
        <h4><fmt:message key="productDetails.notesEn" bundle="${legend}"/></h4>
        <i><p>${product.notesEn}</p></i></b><br/>
        <div class="button_div">
            <button class="bigbutton" onclick="history.back(); return false;">
                <fmt:message key="productDetails.back" bundle="${buttons}"/>
            </button>
        </div>
    </form>
</div>
<t:colontitle/>
</body>
</html>
