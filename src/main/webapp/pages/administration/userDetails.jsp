<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="userProfile.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='/pages/main_style.css' %>
</style>
<body>
<h1><fmt:message key="userProfile.h1" bundle="${legend}"/></h1>
<div class = index_div>
    <c:if test="${param.edit == false}">
        <form name="LoginForm" method="get" action="project" >
            <input type="hidden" name="command" value="getUserDetails" />
            <input type="hidden" name="userId" value="${user.id}"/>
            <div class="inner_div">
                <%--<input type="text" name="code" size="24" class="input" value="${product.code}" required/><br/>--%>
                <p><fmt:message key="userProfile.name" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.name}"/></i></b>
                </p>
                <p><fmt:message key="userProfile.role" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.userRole}"/></i></b>
                </p>
                <p><fmt:message key="userProfile.email" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.email}"/></i></b>
                </p>
                <p><fmt:message key="userProfile.tel" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.phoneNumber}"/></i></b>
                </p>
                <p><fmt:message key="userProfile.address" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.address}"/></i></b>
                </p>
                <p><fmt:message key="userProfile.notes" bundle="${legend}"/><br/>
                    <b><i><c:out value="${user.notes}"/></i></b>
                </p>
            </div>
            <form name="LoginForm" method="get" action="project" >
                <input type="hidden" name="command" value="getUserDetails" />
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="hidden" name="edit" value="true" />
                <input type="hidden" name="delete" value="false" />
                <button class="bigbutton"><fmt:message key="userProfile.edit" bundle="${buttons}"/></button>
            </form>
            <form name="LoginForm" method="get" action="project" >
                <input type="hidden" name="command" value="getUserDetails" />
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="hidden" name="edit" value="false" />
                <input type="hidden" name="delete" value="true" />
                <button class="bigbutton">
                    <fmt:message key="userProfile.delete" bundle="${buttons}"/>
                </button>
            </form>
            <c:if test="${param.delete == true}">
                <form name="LoginForm" method="get" action="project" >
                    <input type="hidden" name="command" value="deleteUserAccount" />
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <button class="bigbutton">
                        <fmt:message key="userProfile.deleteConfirmation" bundle="${buttons}"/>
                    </button>
                    <button class="bigbutton" onclick="history.back(); return false;">
                        <fmt:message key="userProfile.deleteNegation" bundle="${buttons}"/>
                    </button>
                </form>
            </c:if>
            <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="userProfile.back" bundle="${buttons}"/></button>
        </form>
    </c:if>



    <c:if test="${param.edit == true}">
        <form name="LoginForm" method="post" action="project" >
            <input type="hidden" name="command" value="saveUserProfileAsAdmin" />
            <input type="hidden" name="userId" value="<c:out value="${user.id}"/>" />
            <h4><fmt:message key="userProfile.name" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.name}"/>" name="name" size="36" class="input" required/><br/>

            <h4><fmt:message key="userProfile.role" bundle="${legend}"/></h4>
            <select value="<c:out value="${user.userRole}"/>" name="userRole" style="height: 25px; width: 290px;" required>
                <c:if test="${user.userRole == 'USER'}">
                    <option selected>USER</option>
                    <option>CASHIER</option>
                    <option>SENIOR_CASHIER</option>
                    <option>MERCHANT</option>
                    <option>ADMIN</option>
                </c:if>
                <c:if test="${user.userRole == 'CASHIER'}">
                    <option>USER</option>
                    <option selected>CASHIER</option>
                    <option>SENIOR_CASHIER</option>
                    <option>MERCHANT</option>
                    <option>ADMIN</option>
                </c:if>
                <c:if test="${user.userRole == 'SENIOR_CASHIER'}">
                    <option>USER</option>
                    <option>CASHIER</option>
                    <option selected>SENIOR_CASHIER</option>
                    <option>MERCHANT</option>
                    <option>ADMIN</option>
                </c:if>
                <c:if test="${user.userRole == 'MERCHANT'}">
                    <option>USER</option>
                    <option>CASHIER</option>
                    <option>SENIOR_CASHIER</option>
                    <option selected>MERCHANT</option>
                    <option>ADMIN</option>
                </c:if>
                <c:if test="${user.userRole == 'ADMIN'}">
                    <option>USER</option>
                    <option>CASHIER</option>
                    <option>SENIOR_CASHIER</option>
                    <option>MERCHANT</option>
                    <option selected>ADMIN</option>
                </c:if>
            </select><br/>

            <%--<h4><fmt:message key="userProfile.role" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.userRole}"/>" name="userRole" size="36" class="input" required/><br/>--%>
            <h4><fmt:message key="userProfile.password" bundle="${legend}"/></h4>
            <input type="password" value="<c:out value="${user.password}"/>" name="password" size="36" class="input" required/><br/><br/>
            <h4><fmt:message key="userProfile.tel" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.phoneNumber}"/>" name="phone" size="36" class="input" /><br/><br/>
            <h4><fmt:message key="userProfile.email" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.email}"/>" name="email" size="36" class="input" /><br/><br/>
            <h4><fmt:message key="userProfile.address" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.address}"/>" name="address" size="36" class="input" /><br/><br/>
            <h4><fmt:message key="userProfile.notes" bundle="${legend}"/></h4>
            <input type="text" value="<c:out value="${user.notes}"/>" name="notes" size="36" class="input" /><br/><br/>
            <button class="bigbutton"><fmt:message key="userProfile.save" bundle="${buttons}"/></button>
            <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="userProfile.back" bundle="${buttons}"/></button>
        </form>
    </c:if>
</div>
<t:colontitle/>
</body>
</html>