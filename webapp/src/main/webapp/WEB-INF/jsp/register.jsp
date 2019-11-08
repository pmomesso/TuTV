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
    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/register.js"/>"></script>

    <meta name="title" content="TUTV">
    <title>TUTV</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/shortcuticon.png"/>">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/tvst.css"/>">
</head>
<body>
<div class="full-overlay"></div>
<div class="container h-100">
    <div class="row justify-content-center h-100">
        <div class="col-lg-8 col-sm-11 align-self-center white-background">
            <div class="w-100 divide-section-bottom text-center">
                <img src="<c:url value="/resources/img/shortcuticon.png"/>" alt="TUTV">
                <span class="title-page"><spring:message code="register.title"/></span>
            </div>
            <c:url value='register' var="myVar"/>
            <form:form id="registerForm" modelAttribute="registerForm" action="${myVar}" method="post" enctype="application/x-www-form-urlencoded">
                <div class="container">
                    <div class="row w-100">
                        <div class="col-5 h-100 align-self-center">
                            <form:label class="ml-lg-5" path="mail"><spring:message code="register.mail"/></form:label>
                        </div>
                        <div class="col-7 align-self-center">
                            <form:input class="m-3 w-100" path="mail" type="text" minlength="6" maxlength="60"/>
                            <form:errors path="mail" element="p" cssClass="m-3 error"/>
                            <c:if test="${mailExists}">
                                <p class="m-3 error"><spring:message code="register.mailExists"/></p>
                            </c:if>
                        </div>
                    </div>
                    <div class="row w-100">
                        <div class="col-5 h-100 align-self-center">
                            <form:label class="ml-lg-5" path="username"><spring:message code="register.username"/></form:label>
                        </div>
                        <div class="col-7 align-self-center">
                            <form:input class="m-3 w-100" path="username" type="text" minlength="6" maxlength="32"/>
                            <form:errors path="username" element="p" cssClass="m-3 error"/>
                            <c:if test="${usernameExists}">
                                <p class="m-3 error"><spring:message code="profile.usernameExists"/></p>
                            </c:if>
                        </div>
                    </div>
                    <div class="row w-100">
                        <div class="col-5 h-100 align-self-center">
                            <form:label class="ml-lg-5" path="password"><spring:message code="register.password"/></form:label>
                        </div>
                        <div class="col-7 align-self-center">
                            <form:input id="passwordInput" class="m-3 w-100" path="password" type="password" minlength="6" maxlength="32"/>
                            <form:errors path="password" element="p" cssClass="m-3 error"/>
                        </div>
                    </div>
                    <div class="row w-100">
                        <div class="col-5 h-100 align-self-center">
                            <form:label class="ml-lg-5" path="repeatPassword"><spring:message code="register.repeatPassword"/></form:label>
                        </div>
                        <div class="col-7 align-self-center">
                            <form:input id="repeatPasswordInput" class="m-3 w-100" path="repeatPassword" type="password" minlength="6" maxlength="32"/>
                            <form:errors path="repeatPassword" element="p" cssClass="m-3 error"/>
                            <p id="unmatchedPasswordError" class="m-3 error" style="display: none"><spring:message code="register.unmatchedPassword"/></p>
                        </div>
                    </div>
                </div>
                <div class="text-center m-3">
                    <input class="tutv-button" type="submit" value="<spring:message code="register.submit"/>"/>
                </div>
            </form:form>
            <div class="divide-section-top text-center">
                <span><spring:message code="register.haveaccount"/></span>
                <a href="<c:url value="/login"/>"><spring:message code="register.login"/></a>
                <br>
                <a href="<c:url value="/"/>"><spring:message code="register.continue"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>