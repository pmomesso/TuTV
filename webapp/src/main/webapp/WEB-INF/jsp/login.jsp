<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2><spring:message code="login.title"/></h2>
    <form:form modelAttribute="loginForm" action="/login" method="post" enctype="application/x-www-form-urlencoded">
        <div>
            <form:label path="username"><spring:message code="login.username"/></form:label>
            <form:input path="username" type="text"/>
            <form:errors path="username" element="p" cssClass="error"/>
        </div>
        <div>
            <form:label path="password"><spring:message code="login.password"/></form:label>
            <form:input path="password" type="password"/>
            <form:errors path="password" element="p" cssClass="error"/>
        </div>
        <div>
            <form:label path="rememberme">
                <form:checkbox path="rememberme"/>
                <spring:message code="login.rememberme"/>
            </form:label>
        </div>
        <div>
            <input type="submit" value="<spring:message code="login.submit"/>"/>
        </div>
    </form:form>
</body>
</html>
