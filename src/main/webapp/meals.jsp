<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="webjars/fontawesome/4.7.0/css/font-awesome.min.css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<div class="container">
    <h2>Meals</h2>
    <a href="meals?action=add" class="btn btn-primary"><i class="fa fa-plus"></i> Добавить</a>
    <table class="table table-striped">
        <thead>
        <th>Дата / Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действие</th>
        </thead>
        <tbody>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td><%=meal.getDateTime().format(TimeUtil.FORMATTER)%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <a href="meals?id=${meal.id}&action=edit"><i class="fa fa-pencil"></i></a>
                </td>
                <td>
                    <a href="meals?id=${meal.id}&action=delete"><i class="fa fa-times"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>