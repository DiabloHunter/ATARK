<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Авторизация</h1>
<div class = index_div>
    <form action="/auth" method="post" enctype="multipart/form-data">
        <h2><c:out value="${errorMessage}" /></h2>
        <input type="submit" class="bigbutton" value="Назад" onclick="history.back(); return false;"/>
    </form>
</div>
<t:colontitle/>
</body>
</html>