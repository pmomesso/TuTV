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
    <link rel="shortcut icon" type="image/x-icon" href="./../../resources/img/shortcuticon.png">

    <!-- Bootstrap core CSS -->
    <link href="../../resources/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="/resources/css/tvst.css">

    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/popper.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>
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
                  <li class="calendar ">
                    <a href="/" title="TV Calendar">
                      <img class="logo logo_icon" src="./../../resources/img/calendar.png" alt="Calendar">
                      <span>Calendar</span>
                    </a>
                  </li>
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
              <section id="fantasy-shows" class="simple">
                <h1>Fantasy</h1>
                <a href="/en/show/browse?genre=fantasy" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-fantasy-item-305288" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/305288">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/305288/1372844-4-optimized.jpg"
                             alt="Stranger Things">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/305288" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/305288">Stranger Things</a></h2>
                      <a href="/en/show/305288" class="secondary-link">3,833,899 followers</a>
                    </div>
                  </li>
                  <li id="show-fantasy-item-295685" class=" ">
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
                  <li id="show-fantasy-item-331753" class=" ">
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
                  <li id="show-fantasy-item-78901" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/78901">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/78901/977447-4-optimized.jpg"
                             alt="Supernatural">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/78901" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/78901">Supernatural</a></h2>
                      <a href="/en/show/78901" class="secondary-link">2,026,516 followers</a>
                    </div>
                  </li>
                  <li id="show-fantasy-item-121361" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/121361">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/121361/1212771-4-optimized.jpg"
                             alt="Game of Thrones">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/121361" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/121361">Game of Thrones</a></h2>
                      <a href="/en/show/121361" class="secondary-link">4,556,026 followers</a>
                    </div>
                  </li>
                  <li id="show-fantasy-item-348545" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/348545">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/348545/1357928-4-optimized.jpg"
                             alt="Demon Slayer: Kimetsu no Yaiba">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/348545" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/348545">Demon Slayer: Kimetsu no Yaiba</a></h2>
                      <a href="/en/show/348545" class="secondary-link">95,653 followers</a>
                    </div>
                  </li>
                  <li id="show-fantasy-item-81797" class=" ">
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
                  <li id="show-fantasy-item-300472" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/300472">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/300472/1372243-4-optimized.jpg"
                             alt="Preacher">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/300472" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/300472">Preacher</a></h2>
                      <a href="/en/show/300472" class="secondary-link">271,669 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="action-shows" class="simple">
                <h1><i class="icon-tvst-genre-action"></i>Action</h1>
                <a href="/en/show/browse?genre=action" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-action-item-268592" class=" ">
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
                  <li id="show-action-item-295685" class=" ">
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
                  <li id="show-action-item-355567" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/355567">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/355567/1337843-4-optimized.jpg"
                             alt="The Boys">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/355567" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/355567">The Boys</a></h2>
                      <a href="/en/show/355567" class="secondary-link">161,664 followers</a>
                    </div>
                  </li>
                  <li id="show-action-item-331753" class=" ">
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
                  <li id="show-action-item-78901" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/78901">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/78901/977447-4-optimized.jpg"
                             alt="Supernatural">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/78901" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/78901">Supernatural</a></h2>
                      <a href="/en/show/78901" class="secondary-link">2,026,516 followers</a>
                    </div>
                  </li>
                  <li id="show-action-item-81797" class=" ">
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
                  <li id="show-action-item-348545" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/348545">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/348545/1357928-4-optimized.jpg"
                             alt="Demon Slayer: Kimetsu no Yaiba">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/348545" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/348545">Demon Slayer: Kimetsu no Yaiba</a></h2>
                      <a href="/en/show/348545" class="secondary-link">95,653 followers</a>
                    </div>
                  </li>
                  <li id="show-action-item-79824" class=" ">
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
                </ul>
              </section>
              <section id="animation-shows" class="simple">
                <h1><i class="icon-tvst-genre-animation"></i>Animation</h1>
                <a href="/en/show/browse?genre=animation" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-animation-item-331753" class=" ">
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
                  <li id="show-animation-item-348545" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/348545">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/348545/1357928-4-optimized.jpg"
                             alt="Demon Slayer: Kimetsu no Yaiba">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/348545" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/348545">Demon Slayer: Kimetsu no Yaiba</a></h2>
                      <a href="/en/show/348545" class="secondary-link">95,653 followers</a>
                    </div>
                  </li>
                  <li id="show-animation-item-81797" class=" ">
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
                  <li id="show-animation-item-79824" class=" ">
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
                  <li id="show-animation-item-357019" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/357019">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/357019/1371485-4-optimized.jpg"
                             alt="Arifureta - From Commonplace to World's Strongest">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/357019" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/357019">Arifureta - From Commonplace to World's Strongest</a></h2>
                      <a href="/en/show/357019" class="secondary-link">18,400 followers</a>
                    </div>
                  </li>
                  <li id="show-animation-item-332353" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/332353">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/332353/1266977-4-optimized.jpg"
                             alt="Final Space">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/332353" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/332353">Final Space</a></h2>
                      <a href="/en/show/332353" class="secondary-link">137,678 followers</a>
                    </div>
                  </li>
                  <li id="show-animation-item-74796" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/74796">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/74796/1274654-4-optimized.jpg" alt="Bleach">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/74796" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/74796">Bleach</a></h2>
                      <a href="/en/show/74796" class="secondary-link">214,641 followers</a>
                    </div>
                  </li>
                  <li id="show-animation-item-114801" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/114801">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/114801/1121749-4-optimized.jpg"
                             alt="Fairy Tail">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/114801" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/114801">Fairy Tail</a></h2>
                      <a href="/en/show/114801" class="secondary-link">509,691 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="thriller-shows" class="simple">
                <h1><i class="icon-tvst-genre-thriller"></i>Thriller</h1>
                <a href="/en/show/browse?genre=thriller" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-thriller-item-328708" class=" ">
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
                  <li id="show-thriller-item-268592" class=" ">
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
                  <li id="show-thriller-item-290853" class=" ">
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
                  <li id="show-thriller-item-327417" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/327417">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/327417/1376927-4-optimized.jpg"
                             alt="Money Heist">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/327417" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/327417">Money Heist</a></h2>
                      <a href="/en/show/327417" class="secondary-link">2,358,279 followers</a>
                    </div>
                  </li>
                  <li id="show-thriller-item-281620" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/281620">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/281620/1365349-4-optimized.jpg"
                             alt="How to Get Away with Murder">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/281620" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/281620">How to Get Away with Murder</a></h2>
                      <a href="/en/show/281620" class="secondary-link">1,917,560 followers</a>
                    </div>
                  </li>
                  <li id="show-thriller-item-294741" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/294741">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/294741/1268466-4-optimized.jpg"
                             alt="Locked Up">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/294741" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/294741">Locked Up</a></h2>
                      <a href="/en/show/294741" class="secondary-link">94,028 followers</a>
                    </div>
                  </li>
                  <li id="show-thriller-item-253463" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/253463">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/253463/1252088-4-optimized.jpg"
                             alt="Black Mirror">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/253463" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/253463">Black Mirror</a></h2>
                      <a href="/en/show/253463" class="secondary-link">2,204,955 followers</a>
                    </div>
                  </li>
                  <li id="show-thriller-item-153021" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/153021">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/153021/1298939-4-optimized.jpg"
                             alt="The Walking Dead">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/153021" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/153021">The Walking Dead</a></h2>
                      <a href="/en/show/153021" class="secondary-link">3,666,881 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="adventure-shows" class="simple">
                <h1><i class="icon-tvst-genre-adventure"></i>Adventure</h1>
                <a href="/en/show/browse?genre=adventure" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-adventure-item-305288" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/305288">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/305288/1372844-4-optimized.jpg"
                             alt="Stranger Things">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/305288" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/305288">Stranger Things</a></h2>
                      <a href="/en/show/305288" class="secondary-link">3,833,899 followers</a>
                    </div>
                  </li>
                  <li id="show-adventure-item-355567" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/355567">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/355567/1337843-4-optimized.jpg"
                             alt="The Boys">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/355567" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/355567">The Boys</a></h2>
                      <a href="/en/show/355567" class="secondary-link">161,664 followers</a>
                    </div>
                  </li>
                  <li id="show-adventure-item-121361" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/121361">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/121361/1212771-4-optimized.jpg"
                             alt="Game of Thrones">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/121361" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/121361">Game of Thrones</a></h2>
                      <a href="/en/show/121361" class="secondary-link">4,556,026 followers</a>
                    </div>
                  </li>
                  <li id="show-adventure-item-81797" class=" ">
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
                  <li id="show-adventure-item-79824" class=" ">
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
                  <li id="show-adventure-item-300472" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/300472">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/300472/1372243-4-optimized.jpg"
                             alt="Preacher">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/300472" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/300472">Preacher</a></h2>
                      <a href="/en/show/300472" class="secondary-link">271,669 followers</a>
                    </div>
                  </li>
                  <li id="show-adventure-item-175001" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/175001">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/175001/1224128-4-optimized.jpg"
                             alt="Teen Wolf">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/175001" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/175001">Teen Wolf</a></h2>
                      <a href="/en/show/175001" class="secondary-link">1,828,508 followers</a>
                    </div>
                  </li>
                  <li id="show-adventure-item-332353" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/332353">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/332353/1266977-4-optimized.jpg"
                             alt="Final Space">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/332353" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/332353">Final Space</a></h2>
                      <a href="/en/show/332353" class="secondary-link">137,678 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="mini-series-shows" class="simple">
                <h1><i class="icon-tvst-genre-mini-series"></i>Mini-series</h1>
                <a href="/en/show/browse?genre=mini-series" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-mini-series-item-360388" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/360388">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/360388/1365509-4-optimized.jpg"
                             alt="When They See Us">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/360388" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/360388">When They See Us</a></h2>
                      <a href="/en/show/360388" class="secondary-link">172,000 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-348598" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/348598">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/348598/1369051-4-optimized.jpg"
                             alt="Years and Years">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/348598" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/348598">Years and Years</a></h2>
                      <a href="/en/show/348598" class="secondary-link">34,571 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-353718" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/353718">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/353718/1314495-4-optimized.jpg"
                             alt="Victim Number 8">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/353718" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/353718">Victim Number 8</a></h2>
                      <a href="/en/show/353718" class="secondary-link">1,368 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-349738" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/349738">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/349738/1320782-4-optimized.jpg"
                             alt="Escape at Dannemora">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/349738" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/349738">Escape at Dannemora</a></h2>
                      <a href="/en/show/349738" class="secondary-link">28,693 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-299855" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/299855">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/299855/1107655-4-optimized.jpg"
                             alt="And Then There Were None">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/299855" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/299855">And Then There Were None</a></h2>
                      <a href="/en/show/299855" class="secondary-link">94,849 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-360893" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/360893">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/360893/1357320-4-optimized.jpg"
                             alt="Chernobyl">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/360893" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/360893">Chernobyl</a></h2>
                      <a href="/en/show/360893" class="secondary-link">604,353 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-359569" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/359569">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/359569/1339516-4-optimized.jpg"
                             alt="Good Omens">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/359569" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/359569">Good Omens</a></h2>
                      <a href="/en/show/359569" class="secondary-link">152,272 followers</a>
                    </div>
                  </li>
                  <li id="show-mini-series-item-367507" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/367507">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/367507/1379793-4-optimized.jpg"
                             alt="Green Frontier">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/367507" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/367507">Green Frontier</a></h2>
                      <a href="/en/show/367507" class="secondary-link">902 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="science-fiction-shows" class="simple">
                <h1><i class="icon-tvst-genre-science-fiction"></i>Science-Fiction</h1>
                <a href="/en/show/browse?genre=science-fiction" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-science-fiction-item-268592" class=" ">
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
                  <li id="show-science-fiction-item-290853" class=" ">
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
                  <li id="show-science-fiction-item-355567" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/355567">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/355567/1337843-4-optimized.jpg"
                             alt="The Boys">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/355567" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/355567">The Boys</a></h2>
                      <a href="/en/show/355567" class="secondary-link">161,664 followers</a>
                    </div>
                  </li>
                  <li id="show-science-fiction-item-263365" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/263365">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/263365/1358599-4-optimized.jpg"
                             alt="Marvel's Agents of S.H.I.E.L.D.">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/263365" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/263365">Marvel's Agents of S.H.I.E.L.D.</a></h2>
                      <a href="/en/show/263365" class="secondary-link">1,145,809 followers</a>
                    </div>
                  </li>
                  <li id="show-science-fiction-item-334824" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/334824">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/334824/1248817-4-optimized.jpg" alt="Dark">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/334824" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/334824">Dark</a></h2>
                      <a href="/en/show/334824" class="secondary-link">1,215,730 followers</a>
                    </div>
                  </li>
                  <li id="show-science-fiction-item-332353" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/332353">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/332353/1266977-4-optimized.jpg"
                             alt="Final Space">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/332353" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/332353">Final Space</a></h2>
                      <a href="/en/show/332353" class="secondary-link">137,678 followers</a>
                    </div>
                  </li>
                  <li id="show-science-fiction-item-365722" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/365722">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/365722/1374427-4-optimized.jpg"
                             alt="Another Life (2019)">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/365722" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/365722">Another Life (2019)</a></h2>
                      <a href="/en/show/365722" class="secondary-link">48,794 followers</a>
                    </div>
                  </li>
                  <li id="show-science-fiction-item-73739" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/73739">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/73739/1028654-4-optimized.jpg" alt="Lost">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/73739" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/73739">Lost</a></h2>
                      <a href="/en/show/73739" class="secondary-link">1,028,536 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="horror-shows" class="simple">
                <h1><i class="icon-tvst-genre-horror"></i>Horror</h1>
                <a href="/en/show/browse?genre=horror" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-horror-item-290853" class=" ">
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
                  <li id="show-horror-item-305288" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/305288">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/305288/1372844-4-optimized.jpg"
                             alt="Stranger Things">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/305288" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/305288">Stranger Things</a></h2>
                      <a href="/en/show/305288" class="secondary-link">3,833,899 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-95491" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/95491">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/95491/1366299-4-optimized.jpg"
                             alt="The Vampire Diaries">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/95491" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/95491">The Vampire Diaries</a></h2>
                      <a href="/en/show/95491" class="secondary-link">2,122,242 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-266883" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/266883">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/266883/980523-4-optimized.jpg"
                             alt="The Originals">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/266883" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/266883">The Originals</a></h2>
                      <a href="/en/show/266883" class="secondary-link">1,570,451 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-281470" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/281470">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/281470/1356144-4-optimized.jpg"
                             alt="iZombie">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/281470" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/281470">iZombie</a></h2>
                      <a href="/en/show/281470" class="secondary-link">637,442 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-153021" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/153021">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/153021/1298939-4-optimized.jpg"
                             alt="The Walking Dead">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/153021" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/153021">The Walking Dead</a></h2>
                      <a href="/en/show/153021" class="secondary-link">3,666,881 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-250487" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/250487">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/250487/1298931-4-optimized.jpg"
                             alt="American Horror Story">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/250487" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/250487">American Horror Story</a></h2>
                      <a href="/en/show/250487" class="secondary-link">2,069,030 followers</a>
                    </div>
                  </li>
                  <li id="show-horror-item-248736" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/248736">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/248736/1039621-4-optimized.jpg" alt="Grimm">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/248736" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/248736">Grimm</a></h2>
                      <a href="/en/show/248736" class="secondary-link">540,504 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="reality-shows" class="simple">
                <h1><i class="icon-tvst-genre-reality"></i>Reality</h1>
                <a href="/en/show/browse?genre=reality" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-reality-item-325091" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/325091">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/325091/1360209-4-optimized.jpg"
                             alt="Run BTS!">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/325091" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/325091">Run BTS!</a></h2>
                      <a href="/en/show/325091" class="secondary-link">32,682 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-280009" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/280009">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/280009/1144797-4-optimized.jpg"
                             alt="Bachelor in Paradise">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/280009" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/280009">Bachelor in Paradise</a></h2>
                      <a href="/en/show/280009" class="secondary-link">20,297 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-277230" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/277230">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/277230/1374393-4-optimized.jpg"
                             alt="Are You the One?">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/277230" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/277230">Are You the One?</a></h2>
                      <a href="/en/show/277230" class="secondary-link">36,154 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-285626" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/285626">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/285626/1131280-4-optimized.jpg"
                             alt="MasterChef Brasil">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/285626" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/285626">MasterChef Brasil</a></h2>
                      <a href="/en/show/285626" class="secondary-link">95,210 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-85002" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/85002">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/85002/1342763-4-optimized.jpg"
                             alt="RuPaul's Drag Race">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/85002" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/85002">RuPaul's Drag Race</a></h2>
                      <a href="/en/show/85002" class="secondary-link">210,072 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-148561" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/148561">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/148561/877695-4-optimized.jpg"
                             alt="Teen Mom">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/148561" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/148561">Teen Mom</a></h2>
                      <a href="/en/show/148561" class="secondary-link">42,954 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-303904" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/303904">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/303904/1153762-4-optimized.jpg"
                             alt="Australian Survivor">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/303904" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/303904">Australian Survivor</a></h2>
                      <a href="/en/show/303904" class="secondary-link">2,196 followers</a>
                    </div>
                  </li>
                  <li id="show-reality-item-78956" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/78956">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/78956/1287148-4-optimized.jpg"
                             alt="So You Think You Can Dance">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/78956" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/78956">So You Think You Can Dance</a></h2>
                      <a href="/en/show/78956" class="secondary-link">40,038 followers</a>
                    </div>
                  </li>
                </ul>
              </section>
              <section id="children-shows" class="simple">
                <h1><i class="icon-tvst-genre-children"></i>Children</h1>
                <a href="/en/show/browse?genre=children" class="show-all">See all ></a>
                <ul class="posters-list shows-list explore-list list-unstyled list-inline">
                  <li id="show-children-item-192061" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/192061">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/192061/955303-4-optimized.jpg"
                             alt="Young Justice">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/192061" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/192061">Young Justice</a></h2>
                      <a href="/en/show/192061" class="secondary-link">214,055 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-76320" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/76320">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/76320/954876-4-optimized.jpg"
                             alt="Justice League">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/76320" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/76320">Justice League</a></h2>
                      <a href="/en/show/76320" class="secondary-link">60,821 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-361915" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/361915">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/361915/1371396-4-optimized.jpg" alt="Bia">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/361915" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/361915">Bia</a></h2>
                      <a href="/en/show/361915" class="secondary-link">2,628 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-294417" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/294417">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/294417/1373972-4-optimized.jpg"
                             alt="Scream: The TV Series">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/294417" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/294417">Scream: The TV Series</a></h2>
                      <a href="/en/show/294417" class="secondary-link">605,271 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-347512" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/347512">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/347512/1325929-4-optimized.jpg"
                             alt="3Below: Tales of Arcadia">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/347512" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/347512">3Below: Tales of Arcadia</a></h2>
                      <a href="/en/show/347512" class="secondary-link">23,001 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-350399" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/350399">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/350399/1295748-4-optimized.jpg"
                             alt="She-Ra and the Princesses of Power">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/350399" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/350399">She-Ra and the Princesses of Power</a></h2>
                      <a href="/en/show/350399" class="secondary-link">31,637 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-76703" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/76703">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/76703/1200577-4-optimized.jpg"
                             alt="Pokémon">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/76703" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/76703">Pokémon</a></h2>
                      <a href="/en/show/76703" class="secondary-link">491,234 followers</a>
                    </div>
                  </li>
                  <li id="show-children-item-250317" class=" ">
                    <div class="image-crop">
                      <a href="/en/show/250317">
                        <img src="https://dg31sz3gwrwan.cloudfront.net/poster/250317/951979-4-optimized.jpg"
                             alt="Yes! Precure 5">
                      </a>
                      <div class="overlay">
                        <a href="/en/show/250317" class="zoom-btn overlay-btn" title="Infos">
                          <i class="zoom-icon icon-tvst-search"></i>
                        </a>
                      </div>
                      <div class="side progress-box">
                        <div class="loader rotating dark small visible"></div>
                      </div>
                    </div>
                    <div class="show-details poster-details">
                      <h2><a href="/en/show/250317">Yes! Precure 5</a></h2>
                      <a href="/en/show/250317" class="secondary-link">1,191 followers</a>
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