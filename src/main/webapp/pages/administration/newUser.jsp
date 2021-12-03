<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="newUser.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>

<body>
<h1><fmt:message key="newUser.h1" bundle="${legend}"/></h1>
<div class = index_div>
    <form name="LoginForm" method="post" action="project" >
        <input type="hidden" name="command" value="addNewUserAsAdmin" />
        <h4><fmt:message key="newUser.name" bundle="${legend}"/></h4>
        <input type="text" name="name" size="36" class="input" required/><br/>
        <h4><fmt:message key="newUser.role" bundle="${legend}"/></h4>
        <select value="<c:out value="${user.userRole}"/>" name="userRole" style="height: 25px; width: 290px;" required>
            <option>USER</option>
            <option>CASHIER</option>
            <option>SENIOR_CASHIER</option>
            <option>MERCHANT</option>
            <option>ADMIN</option>
        </select><br/>
        <h4><fmt:message key="newUser.tel" bundle="${legend}"/></h4>
        <input type="text" name="phone" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="newUser.email" bundle="${legend}"/></h4>
        <input type="text" name="email" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="newUser.address" bundle="${legend}"/></h4>
        <input type="text" name="address" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="newUser.notes" bundle="${legend}"/></h4>
        <input type="text" name="notes" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="newUser.password" bundle="${legend}"/></h4>
        <input type="password" name="password" size="36" class="input" required/><br/><br/>
        <button class="bigbutton"><fmt:message key="newUser.add" bundle="${buttons}"/></button>
        <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="newUser.cancel" bundle="${buttons}"/></button>
    </form>
</div>
<t:colontitle/>
</body>
</html>
