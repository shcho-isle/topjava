<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Добавить еду</title>
</head>
<body>

<form method="POST" action='' name="frmAddMeal">
    Время : <input
        type="text" name="dateTime"
        value="<c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd kk:mm')}" />" /> <br />
    Описание : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Калории : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br /> <input
        type="submit" value="Сохранить" />
</form>
</body>
</html>