<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
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
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">

    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/navigator.js"/>"></script>
</head>
<body id="container" class="">
<div class="body-inner h-100">
    <%@ include file="sideMenu.jsp" %>
    <div class="page-center page-column h-100">
        <div class="page-center-inner h-100">
            <div class="main-block h-100">
                <div class="main-block-container h-100">
                    <div id="home" class="h-100">
                        <section id="new-shows" class="h-100 p-small-0">
                            <h1 class="no-display-small mb-5"><spring:message code="users.title"/></h1>
                            <div class="container bootstrap snippet">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="main-box no-header clearfix">
                                            <div class="main-box-body clearfix">
                                                <div class="table-responsive">
                                                    <table class="table user-list">
                                                        <thead>
                                                        <tr>
                                                            <th><span><spring:message code="users.user"/></span></th>
                                                            <th class="text-center"><span><spring:message code="users.status"/></span></th>
                                                            <th><span><spring:message code="users.email"/></span></th>
                                                            <th><spring:message code="users.action"/></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${users}" var="currUser">
                                                            <tr>
                                                                <td>
<%--                                                                    TODO modify photo--%>
                                                                    <img src="<c:url value="/resources/img/user.png"/>" class="no-display-small" alt="${currUser.userName}">
                                                                    <a href="<c:url value="/profile?id=${currUser.id}"/>" class="user-link">${currUser.userName}</a>
                                                                    <c:choose>
                                                                        <c:when test="${currUser.isAdmin}">
                                                                            <span class="user-subhead">Admin</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="user-subhead"><spring:message code="users.member"/></span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="text-center">
                                                                    <c:choose>
                                                                        <c:when test="${currUser.isBanned}">
                                                                            <span class="label label-danger"><spring:message code="users.banned"/></span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="label label-success"><spring:message code="users.active"/></span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td>
                                                                    <span>${currUser.mailAddress}</span>
                                                                </td>
                                                                <td style="width: 20%;">
                                                                    <c:choose>
                                                                        <c:when test="${currUser.isBanned}">
                                                                            <form action="<c:url value="/unbanUser?userId=${currUser.id}"/>"
                                                                                  method="post" class="float-left" onsubmit="confirmAction(event,'<spring:message code="series.sureUnbanUser"/>')">
                                                                                <button type="submit" class="remove">
                                                                                    <img src="<c:url value="/resources/img/unban.png"/>" title="<spring:message code="series.unban"/>" alt="<spring:message code="series.unban"/>">
                                                                                </button>
                                                                            </form>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form action="<c:url value="/banUser?userId=${currUser.id}"/>"
                                                                                  method="post" class="float-left" onsubmit="confirmAction(event,'<spring:message code="series.sureBanUser"/>')">
                                                                                <button class="heart post-liked" style="font-family: FontAwesome,serif; font-style: normal; font-size: 20px;">&#xf05e</button>
                                                                            </form>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
