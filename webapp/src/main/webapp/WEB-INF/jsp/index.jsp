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
            <form id="global-search" class="navbar-form form-search" action="/">
              <img class="logo logo_icon" src="./../../resources/img/search.png" alt="Search">
              <input style="display:none" type="text" name="search">
              <input type="text" id="global-search-input" name="search" class="show-search" placeholder="Search">
              <a href="/" class="cancel-search">×</a>
            </form>
            <div id="global-search-results"></div>
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
                <h1>New shows</h1>
                <a href="/en/show/browse?filter=beginning" class="show-all">See all ></a>
                <div id="myCarousel" class="carousel slide" data-ride="carousel">
                  <!-- Carousel indicators -->
                  <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                  </ol>
                  <!-- Wrapper for carousel items -->
                  <div class="carousel-inner">
                    <div class="carousel-item active">
                      <img src="https://dg31sz3gwrwan.cloudfront.net/fanart/335425/1378230-0-q80.jpg" itemprop="image">
                      <div class="carousel-caption">
                        <h2>Infinity Train</h2>
                        <h3>5,735 followers</h3>
                        <button class="add-button">Add</button>
                      </div>
                    </div>
                    <div class="carousel-item">
                      <img src="https://dg31sz3gwrwan.cloudfront.net/fanart/320593/1355946-0-q80.jpg" itemprop="image">
                      <div class="carousel-caption">
                        <h2>Alternatino with Arturo Castro</h2>
                        <h3>915 followers</h3>
                        <button class="add-button">Add</button>
                      </div>
                    </div>
                    <div class="carousel-item">
                      <img src="https://dg31sz3gwrwan.cloudfront.net/fanart/339369/1252365-0-q80.jpg" itemprop="image">
                      <div class="carousel-caption">
                        <h2>We Were Tomorrow</h2>
                        <h3>2,907 followers</h3>
                        <button class="add-button">Add</button>
                      </div>
                    </div>
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
              <section id="premieres-finales">
                <h1></i>Premieres and Finales</h1>
                <a href="/" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-premieres-finales-item-260172" class=" ">
                    <div class="image-crop">
                      <a href="/">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/260172/1058334-4-optimized.jpg"
                             alt="Mighty Car Mods">
                      </a>
                      <div class="overlay">
                        <a href="/" class="zoom-btn overlay-btn" title="Infos"></a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/260172">Mighty Car Mods</a></h2>
                      <a href="/en/show/260172" class="secondary-link">1,934 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-361721" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/361721">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/361721/1363219-4-optimized.jpg"
                             alt="Terrace House: Tokyo 2019–2020">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/361721" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/361721">Terrace House: Tokyo 2019–2020</a></h2>
                      <a href="/en/show/361721" class="secondary-link">928 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-267266" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/267266">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/267266/946132-4-optimized.jpg"
                             alt="Rachael Ray">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/267266" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/267266">Rachael Ray</a></h2>
                      <a href="/en/show/267266" class="secondary-link">4,686 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-274399" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/274399">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/274399/1294036-4-optimized.jpg"
                             alt="Honest Trailers">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/274399" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/274399">Honest Trailers</a></h2>
                      <a href="/en/show/274399" class="secondary-link">4,309 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-273005" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/273005">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/273005/1260751-4-optimized.jpg"
                             alt="Ace of Diamond">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/273005" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/273005">Ace of Diamond</a></h2>
                      <a href="/en/show/273005" class="secondary-link">17,684 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-350468" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/350468">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/350468/1370882-4-optimized.jpg"
                             alt="الخلاط">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/350468" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/350468">الخلاط</a></h2>
                      <a href="/en/show/350468" class="secondary-link">504 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-314671" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/314671">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/314671/1286679-4-optimized.jpg"
                             alt="Sin Senos Sí Hay Paraíso">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/314671" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/314671">Sin Senos Sí Hay Paraíso</a></h2>
                      <a href="/en/show/314671" class="secondary-link">8,369 followers</a>
                    </div>
                  </li>
                  <li id="show-premieres-finales-item-85019" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/85019">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/85019/1111760-4-optimized.jpg"
                             alt="Chopped">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/85019" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/85019">Chopped</a></h2>
                      <a href="/en/show/85019" class="secondary-link">7,660 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="drama-shows" class="simple">
                <h1>Drama</h1>
                <a href="/en/show/browse?genre=drama" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-drama-item-328708" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/328708">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/328708/1379930-4-optimized.jpg"
                             alt="Mindhunter">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/328708" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/328708">Mindhunter</a></h2>
                      <a href="/en/show/328708" class="secondary-link">614,451 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-321239" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/321239">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/321239/1276900-4-optimized.jpg"
                             alt="The Handmaid's Tale">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/321239" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/321239">The Handmaid's Tale</a></h2>
                      <a href="/en/show/321239" class="secondary-link">864,439 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-264586" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/264586">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/264586/1294097-4-optimized.jpg"
                             alt="Orange Is the New Black">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/264586" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/264586">Orange Is the New Black</a></h2>
                      <a href="/en/show/264586" class="secondary-link">2,884,524 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-323225" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/323225">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/323225/1305858-4-optimized.jpg"
                             alt="Cable Girls">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/323225" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/323225">Cable Girls</a></h2>
                      <a href="/en/show/323225" class="secondary-link">372,655 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-268592" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/268592">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/268592/1276791-4-optimized.jpg"
                             alt="The 100">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/268592" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/268592">The 100</a></h2>
                      <a href="/en/show/268592" class="secondary-link">2,879,016 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-73762" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/73762">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/73762/1308148-4-optimized.jpg"
                             alt="Grey's Anatomy">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/73762" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/73762">Grey's Anatomy</a></h2>
                      <a href="/en/show/73762" class="secondary-link">2,609,921 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-83610" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/83610">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/83610/1301758-4-optimized.jpg" alt="Glee">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/83610" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/83610">Glee</a></h2>
                      <a href="/en/show/83610" class="secondary-link">840,618 followers</a>
                    </div>
                  </li>
                  <li id="show-drama-item-290853" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/290853">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/290853/1366496-4-optimized.jpg"
                             alt="Fear the Walking Dead">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/290853" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/290853">Fear the Walking Dead</a></h2>
                      <a href="/en/show/290853" class="secondary-link">1,047,252 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="comedy-shows" class="simple">
                <h1>Comedy</h1>
                <a href="/en/show/browse?genre=comedy" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-comedy-item-264586" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/264586">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/264586/1294097-4-optimized.jpg"
                             alt="Orange Is the New Black">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/264586" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/264586">Orange Is the New Black</a></h2>
                      <a href="/en/show/264586" class="secondary-link">2,884,524 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-83610" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/83610">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/83610/1301758-4-optimized.jpg" alt="Glee">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/83610" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/83610">Glee</a></h2>
                      <a href="/en/show/83610" class="secondary-link">840,618 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-79168" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/79168">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/79168/1162841-4-optimized.jpg"
                             alt="Friends">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/79168" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/79168">Friends</a></h2>
                      <a href="/en/show/79168" class="secondary-link">1,571,410 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-295685" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/295685">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/295685/1359067-4-optimized.jpg"
                             alt="Lucifer">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/295685" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/295685">Lucifer</a></h2>
                      <a href="/en/show/295685" class="secondary-link">1,962,238 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-331753" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/331753">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/331753/1333500-4-optimized.jpg"
                             alt="Black Clover">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/331753" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/331753">Black Clover</a></h2>
                      <a href="/en/show/331753" class="secondary-link">209,517 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-81797" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/81797">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/81797/1369141-4-optimized.jpg"
                             alt="One Piece">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/81797" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/81797">One Piece</a></h2>
                      <a href="/en/show/81797" class="secondary-link">732,146 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-79824" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/79824">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/79824/1262728-4-optimized.jpg"
                             alt="Naruto Shippuden">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/79824" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/79824">Naruto Shippuden</a></h2>
                      <a href="/en/show/79824" class="secondary-link">561,785 followers</a>
                    </div>
                  </li>
                  <li id="show-comedy-item-247808" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/247808">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/247808/1373185-4-optimized.jpg" alt="Suits">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/247808" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/247808">Suits</a></h2>
                      <a href="/en/show/247808" class="secondary-link">1,558,581 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </div>
      </div>
    </div>

    </div>

  </body>
</html>