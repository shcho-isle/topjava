<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meal list</title>

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
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>

<table>
    <c:forEach var="m" items="${mealsAttribute}">
        <tr class="${m.exceed ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(m.dateTime, 'yyyy-MM-dd kk:mm')}</td>
            <td>${m.description}</td>
            <td>${m.calories}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>