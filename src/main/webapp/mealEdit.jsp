<%--
  Created by IntelliJ IDEA.
  User: uzer
  Date: 06.10.2019
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="webjars/fontawesome/4.7.0/css/font-awesome.min.css"/>
</head>
<body>
<div class="container">
    <div class="col-6 m-auto">
        <h1>${meal == null ? "Добавить" : "Редактировать"}</h1>
        <form action="meals" method="post">
            <input type="hidden" name="id" value="${meal == null ? "" : meal.id}">
            <div class="form-group">
                <label for="date-time" class="col-form-label">Дата/Время</label>
                <input type="datetime-local" name="date-time"
                       value="${meal == null ? "" : meal.getDateTime().toString()}" class="form-control" id="date-time">
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Описание</label>
                <input type="text" name="description" value="${meal == null ? "" : meal.description}"
                       class="form-control" id="description">
            </div>
            <div class="form-group">
                <label for="calories" class="col-form-label">Калории</label>
                <input type="text" name="calories" value="${meal == null ? "" : meal.calories}" class="form-control"
                       id="calories">
            </div>
            <a href="meals" class="btn btn-secondary"><i class="fa fa-times"></i> Отмена</a>
            <button type="submit" class="btn btn-primary"><i class="fa fa-check"></i> Сохранить</button>
        </form>
    </div>
</div>
</body>
</html>
