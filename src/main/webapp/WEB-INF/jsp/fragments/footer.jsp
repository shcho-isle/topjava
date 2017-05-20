<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="footer">
    <div class="container">
        <spring:message code="app.footer" arguments="<a href=\"https://github.com/shcho-isle/calories-management\" target=_blank>Maven/ Spring/ Security/ JPA(Hibernate)/ Jackson/jQuery</a>"/>
    </div>
</div>

<script type="text/javascript">
    var i18n = [];

    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.status", "users.edit", "meals.edit", "common.search"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>
