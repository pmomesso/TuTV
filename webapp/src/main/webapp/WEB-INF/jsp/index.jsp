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
      <%@ include file="sideMenu.jsp" %>
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
                      <c:choose>
                        <c:when test="${newShow.numFollowers ne 1}">
                          <spring:message code="index.sufix" var="sufix"/>
                        </c:when>
                        <c:otherwise>
                          <c:set var="sufix" value=""></c:set>
                        </c:otherwise>
                      </c:choose>
                      <c:choose>
                        <c:when test="${status.index == 0}">
                          <div class="carousel-item active">
                            <a href="<c:url value="/series?id=${newShow.id}"/>">
                              <img src="<c:url value="https://image.tmdb.org/t/p/original${newShow.bannerUrl}"/>" itemprop="image" alt="${newShow.bannerUrl}">
                              <div class="carousel-caption">
                                <h2>${newShow.name}</h2>
                                <h3><spring:message code="index.followers" arguments="${newShow.numFollowers},${sufix}"/></h3>
                              </div>
                            </a>
                          </div>
                        </c:when>
                        <c:otherwise>
                          <div class="carousel-item">
                            <a href="<c:url value="/series?id=${newShow.id}"/>">
                              <img src="<c:url value="https://image.tmdb.org/t/p/original${newShow.bannerUrl}"/>" itemprop="image" alt="${newShow.bannerUrl}">>
                              <div class="carousel-caption">
                                <h2>${newShow.name}</h2>
                                <h3><spring:message code="index.followers" arguments="${newShow.numFollowers},${sufix}"/></h3>
                              </div>
                            </a>
                          </div>
                        </c:otherwise>
                      </c:choose>
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
                <section id="new-shows">
                  <h2 class="black-font"><c:out value="${entry.key.name}"/></h2>
                  <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                    <c:forEach items="${entry.value}" var="series">
                      <li>
                        <a href="<c:url value="/series?id=${series.id}"/>">
                          <div class="image-crop">
                            <img src="<c:url value="https://image.tmdb.org/t/p/original${series.posterUrl}"/>" alt="${series.name}">
                            <div class="overlay"><span class="zoom-btn overlay-btn"></span></div>
                          </div>
                          <div class="show-details poster-details">
                            <h2>${series.name}</h2>
                            <c:choose>
                              <c:when test="${series.numFollowers ne 1}">
                                <spring:message code="index.sufix" var="sufix2"/>
                              </c:when>
                              <c:otherwise>
                                <c:set var="sufix2" value=""></c:set>
                              </c:otherwise>
                            </c:choose>
                            <span class="secondary-link"><spring:message code="index.followers" arguments="${series.numFollowers},${sufix2}"/></span>
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