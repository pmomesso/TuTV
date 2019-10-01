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
  <body id="container" class="home no-touch white   reduced-right ">
    <div class="body-inner">
      <div class="page-left page-sidebar page-column ">
        <div class="scrollable scrolling-element">
          <%@ include file="sideMenu.jsp" %>
        </div>
      </div>
      <div class="page-center page-column ">
      <div class="page-center-inner">
        <div class="alt-block"></div>
        <div class="main-block">
          <div class="main-block-container">
            <div id="explore">
              <section id="new-shows">
                <h1><spring:message code="index.newShows"/></h1>
                <div id="myCarousel" class="carousel slide" data-ride="carousel">
                  <!-- Carousel indicators -->
                  <ol class="carousel-indicators">
                    <c:forEach items="${newShows}" var="newShow" varStatus="status">
                      <c:choose>
                        <c:when test="${status.index == 0}">
                          <li data-target="#myCarousel" data-slide-to="${status.index}" class="active"></li>
                        </c:when>
                        <c:otherwise>
                          <li data-target="#myCarousel" data-slide-to="${status.index}"></li>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </ol>
                  <!-- Wrapper for carousel items -->
                  <div class="carousel-inner">
                    <c:forEach items="${newShows}" var="newShow" varStatus="status">
                      <a href="<c:url value="/series?id=${newShow.id}"/>">
                      <c:choose>
                        <c:when test="${status.index == 0}">
                          <div class="carousel-item active">
                            <img src="${newShow.bannerUrl}" itemprop="image" alt="${newShow.bannerUrl}">
                            <div class="carousel-caption">
                              <h2>${newShow.name}</h2>
                              <h3>${newShow.numFollowers} <spring:message code="index.followers"/></h3>
                            </div>
                          </div>
                        </c:when>
                        <c:otherwise>
                          <div class="carousel-item">
                            <img src="${newShow.bannerUrl}" itemprop="image" alt="${newShow.bannerUrl}">>
                            <div class="carousel-caption">
                              <h2>${newShow.name}</h2>
                              <h3>${newShow.numFollowers} <spring:message code="index.followers"/></h3>
                            </div>
                          </div>
                        </c:otherwise>
                      </c:choose>
                      </a>
                    </c:forEach>
                  </div>
                  <!-- Carousel controls -->
                  <a class="carousel-control-prev" href="#myCarousel" data-slide="prev">
                    <span class="carousel-control-prev-icon"></span>
                  </a>
                  <a class="carousel-control-next" href="#myCarousel" data-slide="next">
                    <span class="carousel-control-next-icon"></span>
                  </a>
                </div>
              </section>
              <c:forEach items="${seriesMap}" var="entry">
                <section id="${entry.key}">
<%--                  TODO tenemos pagina genre?--%>
<%--                  <a href="<c:url value="/series?genre=${entry.key.id}"/>">--%>
                    <h2 class="black-font"><c:out value="${entry.key.name}"/></h2>
<%--                    <span class="show-all"><spring:message code="index.seeAll"/></span>--%>
<%--                  </a>--%>
                  <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                    <c:forEach items="${entry.value}" var="series">
                      <li id="${series.id}" class=" ">
                        <a href="<c:url value="/series?id=${series.id}"/>">
                          <div class="image-crop">
                            <img src="${series.posterUrl}" alt="${series.name}">
                            <div class="overlay"><span class="zoom-btn overlay-btn"></span></div>
                          </div>
                          <div class="show-details poster-details">
                            <h2>${series.name}</h2>
                            <span class="secondary-link">${series.numFollowers} <spring:message code="index.followers"/></span>
                          </div>
                        </a>
                      </li>
                    </c:forEach>
                  </ul>
                </section>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  </body>
</html>