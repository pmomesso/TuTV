<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="string" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
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
            <h2><spring:message code="register.title"/></h2>
            <form:form modelAttribute="registerForm" action="/register" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                        <%--                el path seria registerForm.username porque vengo de ese modelAtributte--%>
                    <form:label path="username"><spring:message code="register.username"/></form:label>
                    <form:input path="username" type="text"/>
                        <%--                le digo que a cada error lo ponga en el elemento <p> con el estilo error--%>
                    <form:errors path="username" element="p" cssClass="error"/>
                </div>
                <div>
                    <form:label path="password"><spring:message code="register.password"/></form:label>
                    <form:input path="password" type="password"/>
                    <form:errors path="password" element="p" cssClass="error"/>
                </div>
                <div>
                    <form:label path="repeatPassword"><spring:message code="register.repeatPassword"/></form:label>
                    <form:input path="repeatPassword" type="password"/>
                    <form:errors path="repeatPassword" element="p" cssClass="error"/>
                </div>
                <div>
                        <%--                como no tiene un path de mi modelo no necesito usar el de spring--%>
                    <input type="submit" value="<spring:message code="register.submit"/>"/>
                </div>
            </form:form>
            <div>
                <span><spring:message code="register.haveaccount"/></span>
                <a href="<c:url value="/login"/>"><spring:message code="register.login"/></a>
            </div>
        </div>
    </div>
    </body>
</html>