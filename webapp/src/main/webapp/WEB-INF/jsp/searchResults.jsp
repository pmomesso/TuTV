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

    <meta name="title" content="TUTV - Watch and track TV shows online">
    <title>TUTV - Watch and track TV shows online</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/shortcuticon.png"/>">

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/tvst.css"/>">

    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
</head>

<body id="container" class="home no-touch white   reduced-right ">

<div class="body-inner">
    <%@ include file="sideMenu.jsp" %>
    <div class="page-center page-column ">
        <div class="page-center-inner">
            <div class="alt-block"></div>
            <div class="main-block">
                <div class="main-block-container">
                    <div id="explore">
                        <section id="searchResultsTitle">
                            <h1><spring:message code="search.searchResults"/></h1>
                        </section>
                        <section id="searchResultsSection">
                            <c:choose>
                                <c:when test="${not empty searchResults}">
                                    <ul class="posters-list shows-list explore-list list-unstyled list-inline" style="overflow: visible">
                                        <c:forEach items="${searchResults}" var="series">
                                            <li>
                                                <a href="<c:url value="/series?id=${series.id}"/>">
                                                    <div class="image-crop">
                                                        <img src="<c:url value="https://image.tmdb.org/t/p/original${series.posterUrl}"/>" alt="${series.name}">
                                                        <div class="overlay"><span class="zoom-btn overlay-btn"></span></div>
                                                    </div>
                                                    <div class="show-details poster-details">
                                                        <h2>${series.name}</h2>
                                                        <c:choose>
                                                            <c:when test="${series.followers ne 1}">
                                                                <spring:message code="index.sufix" var="sufix"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="sufix" value=""/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <span class="secondary-link"><spring:message code="index.followers" arguments="${series.followers},${sufix}"/></span>
                                                    </div>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <h4><spring:message code="search.noResults"/></h4>
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
