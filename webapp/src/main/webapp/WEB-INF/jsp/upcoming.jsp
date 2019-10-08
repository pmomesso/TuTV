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
                            <c:choose>
                                <c:when test="${empty upcoming}">
                                    <div class="container h-100">
                                        <div class="row justify-content-center h-100">
                                            <div class="col-lg-8 col-sm-12 align-self-center">
                                                <div class="text-center">
                                                    <h2><spring:message code="upcoming.noshows"/></h2>
                                                </div>
                                                <div class="text-center">
                                                    <img src="<c:url value="/resources/img/noshows.png"/>" alt="<spring:message code="watchlist.Noshows"/>">
                                                </div>
                                                <div class="text-center m-4">
                                                    <h4><spring:message code="watchlist.discover"/></h4>
                                                </div>
                                                <div class="text-center m-4">
                                                    <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <h1><spring:message code="upcoming.title"/></h1>
                                    <ul class="to-watch-list posters-list list-unstyled list-inline single-row">
                                        <c:forEach items="${upcoming}" var="series">
                                            <li>
                                                <h4>${series.seasons[0].episodeList[0].airing}</h4>
                                                <a href="<c:url value="/series?id=${series.id}"/>">
                                                    <div class="image-crop">
                                                        <img src="<c:url value="https://image.tmdb.org/t/p/original${series.posterUrl}"/>" alt="${series.name}">
                                                        <div class="progress">
                                                            <div class="progress-bar progress-bar-success uncomplete w-100" role="progressbar">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </a>
                                                <div class="episode-details poster-details">
                                                    <h2><spring:message code="watchlist.season"/><fmt:formatNumber pattern="00" value="${series.seasons[0].seasonNumber}"/>E<fmt:formatNumber pattern="00" value="${series.seasons[0].episodeList[0].episodeNumber}"/></h2>
                                                    <a class="nb-reviews-link secondary-link" href="<c:url value="/series?id=${series.id}"/>">${series.name}</a>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>
                            </c:choose>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
