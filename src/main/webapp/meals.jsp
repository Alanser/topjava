<%@ page import="ru.javawebinar.topjava.web.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <br><br>

    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter">
        <div style="border: #777 solid 1px; padding: 2em;display: flex; flex-direction: column; align-content: space-between; width: 800px">
            <div style="display: flex; justify-content: space-around">
                <div>
                    <label>
                        От даты:
                        <input type="date" name="startDate" value="${param.startDate}">
                    </label>
                    <label>
                        До даты:
                        <input type="date" name="endDate" value="${param.endDate}">
                    </label>
                </div>
                <div>
                    <label>
                        От Времени:
                        <input type="time" name="startTime" value="${param.startTime}">
                    </label>
                    <label>
                        До Времени:
                        <input type="time" name="endTime" value="${param.endTime}">
                    </label>
                </div>
            </div>
            <div style="height: 20px;"></div>
            <div style="display: flex;justify-content: flex-end">
                <button type="button"><a href="meals">Отменить</a></button>
                <button type="submit">Отфильтровать</button>
            </div>
        </div>
    </form>
    <br><br>
    <button type="button"><a href="meals?action=create">Add Meal</a></button>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>