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
    <title>TUTV</title>
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
        <div class="container h-100">
            <div class="row justify-content-center h-100">
                <div class="col-3 align-self-center white-background">
<%--                    COMO MODEL ATTRIBUTE ESTA: "user" (user loggeado) y "userProfile" (de quien se pide el profile)--%>
<%--                    Si coinciden sus ids, habria que dejarlo modificar sus campos--%>
                    <div class="text-center">
                        ${userProfile.userName}
                    </div>
                    <div class="text-center">
                        ${userProfile.mailAddress}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
