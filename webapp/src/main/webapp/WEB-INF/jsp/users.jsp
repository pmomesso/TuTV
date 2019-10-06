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
                        <section id="new-shows" class="h-100">
                            <div class="container bootstrap snippet">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="main-box no-header clearfix">
                                            <div class="main-box-body clearfix">
                                                <div class="table-responsive">
                                                    <table class="table user-list">
                                                        <thead>
                                                        <tr>
                                                            <th><span>User</span></th>
                                                            <th class="text-center"><span>Status</span></th>
                                                            <th><span>Email</span></th>
                                                            <th>&nbsp;</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${users}" var="currUser">
                                                            <tr>
                                                                <td>
<%--                                                                    TODO modify photo--%>
                                                                    <img src="https://bootdey.com/img/Content/user_1.jpg" alt="">
                                                                    <a href="<c:url value="/profile?id=${currUser.id}"/>" class="user-link">${currUser.userName}</a>
                                                                    <c:choose>
                                                                        <c:when test="${currUser.isAdmin}">
                                                                            <span class="user-subhead">Admin</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="user-subhead">Member</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td class="text-center">
                                                                    <c:choose>
                                                                        <c:when test="${currUser.isBanned}">
                                                                            <span class="label label-danger">Banned</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="label label-success">Active</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td>
                                                                    <span>${currUser.mailAddress}</span>
                                                                </td>
                                                                <td style="width: 20%;">
                                                                    <a href="#" class="table-link">
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                    </a>
                                                                    <a href="#" class="table-link">
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                    </a>
                                                                    <a href="#" class="table-link danger">
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                    </a>
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
<%--                            <c:choose>--%>
<%--                                <c:when test="${empty watchlist}">--%>
<%--                                    <div class="container h-100">--%>
<%--                                        <div class="row justify-content-center h-100">--%>
<%--                                            <div class="col-lg-8 col-sm-12 align-self-center">--%>
<%--                                                <div class="text-center">--%>
<%--                                                    <h2><spring:message code="watchlist.noshows"/></h2>--%>
<%--                                                </div>--%>
<%--                                                <div class="text-center">--%>
<%--                                                    <img src="<c:url value="/resources/img/noshows2.png"/>" alt="<spring:message code="watchlist.Noshows"/>">--%>
<%--                                                </div>--%>
<%--                                                <div class="text-center m-4">--%>
<%--                                                    <h4><spring:message code="watchlist.discover"/></h4>--%>
<%--                                                </div>--%>
<%--                                                <div class="text-center m-4">--%>
<%--                                                    <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <h1><spring:message code="watchlist.watchNext"/></h1>--%>
<%--                                    <ul class="to-watch-list posters-list list-unstyled list-inline single-row">--%>
<%--                                        <c:forEach items="${watchlist}" var="series">--%>
<%--                                            <li>--%>
<%--                                                <div class="image-crop">--%>
<%--                                                    <form action="<c:url value="/viewEpisode?seriesId=${series.id}&episodeId=${series.seasons[0].episodeList[0].id}"/>"--%>
<%--                                                          method="post">--%>
<%--                                                        <button type="submit" style="font-family: FontAwesome,serif; font-style: normal" class="check-watchlist">&#xf058</button>--%>
<%--                                                    </form>--%>
<%--                                                    <img src="<c:url value="${series.posterUrl}"/>" alt="${series.name}">--%>
<%--                                                    <div class="progress">--%>
<%--                                                        <div class="progress-bar progress-bar-success uncomplete w-100" role="progressbar">--%>
<%--                                                        </div>--%>
<%--                                                    </div>--%>
<%--                                                </div>--%>
<%--                                                <div class="episode-details poster-details">--%>
<%--                                                    <h2><spring:message code="watchlist.season"/><fmt:formatNumber pattern="00" value="${series.seasons[0].seasonNumber}"/>E<fmt:formatNumber pattern="00" value="${series.seasons[0].episodeList[0].episodeNumber}"/></h2>--%>
<%--                                                    <a class="nb-reviews-link secondary-link" href="<c:url value="/series?id=${series.id}"/>">${series.name}</a>--%>
<%--                                                </div>--%>
<%--                                            </li>--%>
<%--                                        </c:forEach>--%>
<%--                                    </ul>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
