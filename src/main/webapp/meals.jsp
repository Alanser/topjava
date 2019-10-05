<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<div class="container">
    <h2>Meals</h2>
    <table class="table table-striped">
        <thead>
        <th>Дата / Время</th>
        <th>Описание</th>
        <th>Калории</th>
        </thead>
        <tbody>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td><%=meal.getDateTime().format(TimeUtil.FORMATTER)%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>