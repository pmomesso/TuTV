<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=1040">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="MobileOptimized" content="1040">
    <meta http-equiv="content-language" content="en">

    <meta name="title" content="TUTV">
    <title>TUTV</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/shortcuticon.png"/>">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/tvst.css"/>">
</head>
<body>
<div class="full-overlay">
    <div class="container-center white-background">
        <h2><spring:message code="login.title"/></h2>
        <form:form action="/login" method="post" enctype="application/x-www-form-urlencoded">
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
        <div>
            <span><spring:message code="login.noaccount"/></span>
            <a href="<c:url value="/register"/>"><spring:message code="login.createaccount"/></a>
        </div>
    </div>
</div>
</body>
</html>
