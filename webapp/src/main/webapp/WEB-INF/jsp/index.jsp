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

      <div class="page-left page-sidebar page-column ">
        <div class="scrollable scrolling-element">
          <div class="wrapper">
            <a id="home-link" href="/">
              <img class="logo tutv" src="./../../resources/img/Tutv.png" alt="TUTV"> <span id="home-text">TUTV</span>
            </a> <!--#44d9e6-->
            <%@ include file="../searchForm.html" %>
            <div class="all-left-navs">

              <section id="menu">
                <ul class="menu list-unstyled">
                  <li class="upcoming ">
                    <a href="/" title="Upcoming">
                      <img class="logo logo_icon" src="./../../resources/img/upcoming.png" alt="Upcoming">
                      <span>Upcoming</span>
                    </a>
                  </li>
                  <li class="home ">
                    <a href="/" title="Watchlist">
                      <img class="logo logo_icon" src="./../../resources/img/watchlist.png" alt="Watchlist">
                      <span>Watchlist</span>
                    </a>
                  </li>
                  <li class="profile ">
                    <a href="/" title="Profile">
                      <img class="logo logo_icon" src="./../../resources/img/profile.png" alt="Profile">
                      <span>Profile</span>
                    </a>
                  </li>
                  <li class="explore active">
                    <a href="/" title="Explore">
                      <img class="logo logo_icon" src="./../../resources/img/explore_active.png" alt="Explore">
                      <span>Explore</span>
                    </a>
                  </li>
                </ul>
              </section>
              <section id="user-nav">

                <h1>agusosimani</h1>
                <ul class="menu list-unstyled">
                  <li class="account ">
                    <a href="/" title="Settings">
                      <img class="logo logo_icon" src="./../../resources/img/settings.png" alt="Settings">
                      <span>Settings</span>
                    </a>
                  </li>
                  <li class="help">
                    <a href="/" class="help-btn" title="Help">
                      <img class="logo logo_icon" src="./../../resources/img/help.png" alt="Help">
                      <span>Help</span>
                    </a>
                  </li>
                </ul>

              </section>
              <section>
                <a href="/" class="signout-link" title="Sign out">
                  <img class="logo logo_icon" src="./../../resources/img/sign_out.png" alt="Sign out"><span>Sign out</span>
                </a>
              </section>
            </div>
          </div>
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
                  <h2><c:out value="${entry.key.name}"/></h2>
                  <a href="/series?genre=${entry.key.id}" class="show-all"><spring:message code="index.seeAll"/></a>
                  <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                    <c:forEach items="${entry.value}" var="serie">
                      <li id="${serie.id}" class=" ">
                        <div class="image-crop">
                          <a href="/serie?id=${serie.id}">
                            <img src="${serie.posterUrl}"
                                 alt="${serie.name}">
                          </a>
                          <div class="overlay">
                            <a href="/serie?id=${serie.id}" class="zoom-btn overlay-btn" title="info"></a>
                          </div>
                          <div class="side progress-box">
                            <div class="loader rotating dark small visible"></div>
                          </div>
                        </div>
                        <div class="show-details poster-details">
                          <h2><a href="/serie?id=${serie.id}">${serie.name}</a></h2>
                          <a href="/serie?id=${serie.id}" class="secondary-link">${serie.numFollowers} <spring:message code="index.followers"/></a>
                        </div>
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