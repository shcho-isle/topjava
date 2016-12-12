<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Список еды</title>

    <style>
        tr.green {
            color: green;
        }

        tr.red {
            color: red;
        }
    </style>

</head>


<body>
<h2>Список еды</h2>

<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="m" items="${meals}">
        <tr class="${m.exceed ? 'red' : 'green'}">
            <td>${m.id}</td>
            <td>${f:formatLocalDateTime(m.dateTime)}</td>
            <td>${m.description}</td>
            <td>${m.calories}</td>
            <td><a href="meals?action=edit&id=<c:out value="${m.id}"/>">Обновить</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${m.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Добавить еду</a></p>

</body>
</html>