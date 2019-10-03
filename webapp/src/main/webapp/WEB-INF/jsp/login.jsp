<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

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
<c:url value="/login" var="loginUrl" />
<div class="full-overlay"></div>
<div class="container h-100">
    <div class="row justify-content-center h-100">
        <div class="col-lg-6 col-sm-11 align-self-center white-background">
            <div class="w-100 divide-section-bottom text-center">
                <img src="<c:url value="/resources/img/shortcuticon.png"/>" alt="TUTV">
                <span class="title-page"><spring:message code="login.title"/></span>
            </div>
            <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                <div class="container">
                    <div class="row w-100">
                        <div class="col-4 align-self-center">
                            <label class="ml-lg-4" for="username"><spring:message code="login.username"/></label>
                        </div>
                        <div class="col-8 align-self-center">
                            <input class="m-3 w-100" id="username" name="username" type="text"/>
                        </div>
                    </div>
                    <div class="row w-100">
                        <div class="col-4 align-self-center">
                            <label class="ml-lg-4" for="password"><spring:message code="login.password"/></label>
                        </div>
                        <div class="col-8 align-self-center">
                            <input class="m-3 w-100" id="password" name="password" type="password"/>
                        </div>
                    </div>
                </div>
                <div class="text-center p-3">
                    <label for="rememberme">
                        <input id="rememberme" name="rememberme" type="checkbox"/>
                        <spring:message code="login.rememberme"/>
                    </label>
                </div>
                <div class="text-center m-3">
                    <input class="tutv-button" type="submit" value="<spring:message code="login.submit"/>"/>
                </div>
            </form>
            <div class="divide-section-top text-center">
                <span><spring:message code="login.noaccount"/></span>
                <a href="<c:url value="/register"/>"><spring:message code="login.createaccount"/></a>
                <br>
                <a href="<c:url value="/"/>"><spring:message code="login.continue"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
