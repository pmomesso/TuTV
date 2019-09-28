<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>TUTV - Watch and track TV shows online</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/shortcuticon.png"/>">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/tvst.css"/>">

    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/navigator.js"/>"></script>
</head>
<body id="container" class="">
<div class="body-inner h-100">
    <div class="page-left page-sidebar page-column">
        <div class="scrollable scrolling-element">
            <%@ include file="sideMenu.jsp" %>
        </div>
    </div>
    <div class="page-center page-column h-100">
        <div class="container white-background h-100">
            <div class="row justify-content-center h-100">
                <div class="col-3 align-self-center">
                    <h2><spring:message code="login.title"/></h2>
                    <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                        <div>
                            <label for="username"><spring:message code="login.username"/></label>
                            <input id="username" name="username" type="text"/>
                        </div>
                        <div>
                            <label for="password"><spring:message code="login.password"/></label>
                            <input id="password" name="password" type="password"/>
                        </div>
                        <div>
                            <label path="rememberme">
                                <input name="rememberme" type="checkbox"/>
                                <spring:message code="login.rememberme"/>
                            </label>
                        </div>
                        <div>
                            <input type="submit" value="<spring:message code="login.submit"/>"/>
                        </div>
                    </form>
                    <span><spring:message code="login.noaccount"/></span>
                    <a href="<c:url value="/register"/>"><spring:message code="login.createaccount"/></a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

    <%--    <div class="page-center page-column ">--%>
<%--        <div class="page-center-inner">--%>
<%--            <div class="alt-block"></div>--%>
<%--            <div class="main-block">--%>
<%--                <div id="explore">--%>
<%--                    <section id="new-shows">--%>
<%--                    <div class="container-center white-background">--%>
<%--                        <div>--%>
<%--                            ${user.userName}--%>
<%--                        </div>--%>
<%--                        <div>--%>
<%--                            ${user.mailAddress}--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    </section>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
</html>
