<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <%@ include file="sideMenu.jsp" %>
        </div>
    </div>
    <div class="page-center page-column ">
        <div class="page-center-inner">
            <div class="alt-block"></div>
            <div class="main-block">
                <div class="main-block-container">
                    <div id="show-details" class="show" itemscope="" itemtype="http://schema.org/TVSeries">
                        <meta itemprop="numberOfSeasons" content="1">
                        <meta itemprop="numberOfEpisodes" content="8">
                        <div class="hide" itemscope="" itemprop="episode" itemtype="http://schema.org/Episode">
                            <span itemprop="name">The Name of the Game</span>
                            <span itemprop="episodeNumber">1</span>
                            <span itemprop="description"></span>
                            <span itemprop="datePublished">2019-07-26</span>
                            <span itemprop="url">/en/show/355567/episode/7140390</span>
                        </div>
                        <div class="hide" itemscope="" itemprop="episode" itemtype="http://schema.org/Episode">
                            <span itemprop="name">Cherry</span>
                            <span itemprop="episodeNumber">2</span>
                            <span itemprop="description"></span>
                            <span itemprop="datePublished">2019-07-26</span>
                            <span itemprop="url">/en/show/355567/episode/7299045</span>
                        </div>
                        <div class="hide" itemscope="" itemprop="episode" itemtype="http://schema.org/Episode">
                            <span itemprop="name">Get Some</span>
                            <span itemprop="episodeNumber">3</span>
                            <span itemprop="description"></span>
                            <span itemprop="datePublished">2019-07-26</span>
                            <span itemprop="url">/en/show/355567/episode/7299046</span>
                        </div>
                        <div id="top-banner" class="show-header page-header  smaller">
                            <div class="banner">
                                <div class="banner-image" data-stellar-ratio="0.5"
                                     style="transform: translate3d(0px, 0px, 0px); display: block;">
                                    <img src="https://dg31sz3gwrwan.cloudfront.net/fanart/355567/1370924-0-q80.jpg"
                                         itemprop="image">
                                </div>
                                <div class="overlay"></div>
                                <div class="info-zone">
                                    <div class="container-fluid">
                                        <div class="info-box heading-info">
                                            <h1 itemprop="name">
                                                The Boys </h1>
                                            <h3 class="show-numbers">
                                                <a href="#" class="score-link popover-link">
                                                    Score:&nbsp;
                                                    <span class="rating" itemprop="aggregateRating" itemscope=""
                                                          itemtype="http://schema.org/AggregateRating">
                    <span itemprop="ratingValue">9.78</span>
                    /
                    <span itemprop="bestRating">10</span>
                    <meta itemprop="worstRating" content="0">
                                          <meta itemprop="ratingCount" content="193">
                                      </span>
                                                    <i class="icon-tvst-trending-alt"></i>
                                                </a>
                                            </h3>
                                            <div class="btn-group follow-archive-btns ">
                                                <a href="#" class="cta-btn follow-btn  ">
                                                    <span class="follow"><i class="icon-tvst-follow"></i>Add to my shows</span>
                                                    <span class="unfollow"><i
                                                            class="icon-tvst-small_close"></i>Remove</span>
                                                    <span class="unarchive"><i class="icon-tvst-small_close"></i>Unarchive</span>
                                                    <span class="archive"><i
                                                            class="icon-tvst-archive"></i>Archive</span>
                                                </a>
                                                <a href="#" class="cta-btn archive-btn"><i
                                                        class="icon-tvst-archive"></i></a>
                                            </div>
                                        </div>
                                    </div> <!-- //container -->
                                </div><!-- //info-zone -->
                                <div class="progress">
                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"
                                         aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                    </div>
                                </div><!-- //progress -->
                            </div><!-- //banner -->
                        </div><!-- //show-header #top-banner -->
                        <div id="show-ratings" data-highcharts-chart="0" style=""><a href="#" class="close">×</a>
                            <div class="highcharts-container" id="highcharts-0"
                                 style="position: relative; overflow: hidden; width: 584px; height: 300px; text-align: left; line-height: normal; z-index: 0; font-family: helvetica_lt_condensed_medium, sans-serif; font-weight: 300; left: 0px; top: 0px;">
                                <svg version="1.1"
                                     style="font-family:helvetica_lt_condensed_medium, sans-serif;font-size:12;font-weight:300;"
                                     xmlns="http://www.w3.org/2000/svg" width="584" height="300">
                                    <desc>Created with Highcharts 4.1.7</desc>
                                    <defs>
                                        <clipPath id="highcharts-1">
                                            <rect x="0" y="0" width="502" height="230"></rect>
                                        </clipPath>
                                    </defs>
                                    <rect x="0" y="0" width="584" height="300" strokeWidth="0" fill="#FAFAFA"
                                          class=" highcharts-background"></rect>
                                    <path fill="transparent" d="M 71.5 55 L 71.5 285 568.5 285 568.5 55"></path>
                                    <text x="320" text-anchor="middle" transform="translate(0,0)" style="" y="71"
                                          visibility="visible">S01
                                    </text>
                                    <g class="highcharts-button" style="cursor:default;" stroke-linecap="round"
                                       transform="translate(550,10)"><title>Chart context menu</title>
                                        <rect x="0.5" y="0.5" width="24" height="22" strokeWidth="1" fill="white"
                                              stroke="none" stroke-width="1" rx="2" ry="2"></rect>
                                        <path fill="#E0E0E0" d="M 6 6.5 L 20 6.5 M 6 11.5 L 20 11.5 M 6 16.5 L 20 16.5"
                                              stroke="#666" stroke-width="3" zIndex="1"></path>
                                        <text x="0" zIndex="1" style="color:black;fill:black;" y="15"></text>
                                    </g>
                                    <g class="highcharts-grid" zIndex="1"></g>
                                    <g class="highcharts-grid" zIndex="1">
                                        <path fill="none" d="M 72 285.5 L 574 285.5" stroke="#D8D8D8" stroke-width="1"
                                              zIndex="1" opacity="1"></path>
                                        <path fill="none" d="M 72 227.5 L 574 227.5" stroke="#D8D8D8" stroke-width="1"
                                              zIndex="1" opacity="1"></path>
                                        <path fill="none" d="M 72 170.5 L 574 170.5" stroke="#D8D8D8" stroke-width="1"
                                              zIndex="1" opacity="1"></path>
                                        <path fill="none" d="M 72 112.5 L 574 112.5" stroke="#D8D8D8" stroke-width="1"
                                              zIndex="1" opacity="1"></path>
                                        <path fill="none" d="M 72 55.5 L 574 55.5" stroke="#D8D8D8" stroke-width="1"
                                              zIndex="1" opacity="1"></path>
                                    </g>
                                    <g class="highcharts-axis" zIndex="2"></g>
                                    <g class="highcharts-axis" zIndex="2">
                                        <text x="29" zIndex="7" text-anchor="middle"
                                              transform="translate(0,0) rotate(270 29 170)"
                                              class=" highcharts-yaxis-title"
                                              style="color:#AAA;font-weight:300;fill:#AAA;" visibility="visible"
                                              y="170">ratings
                                        </text>
                                    </g>
                                    <g class="highcharts-series-group" zIndex="3">
                                        <g class="highcharts-series" visibility="visible" zIndex="9"
                                           transform="translate(72,55) scale(1 1)" clip-path="url(#highcharts-1)">
                                            <path fill="none"
                                                  d="M 0 76.80357142857153 L 497.029702970297 87.20833333333309"
                                                  stroke="#dcad43" stroke-width="4" zIndex="1" stroke-linejoin="round"
                                                  stroke-linecap="round"></path>
                                            <path fill="none"
                                                  d="M -10 76.80357142857153 L 0 76.80357142857153 L 497.029702970297 87.20833333333309 L 507.029702970297 87.20833333333309"
                                                  stroke-linejoin="round" visibility="visible"
                                                  stroke="rgba(192,192,192,0.0001)" stroke-width="24" zIndex="2"
                                                  class=" highcharts-tracker" style=""></path>
                                        </g>
                                        <g class="highcharts-markers highcharts-tracker" visibility="visible" zIndex="9"
                                           transform="translate(72,55) scale(1 1)" clip-path="url(#highcharts-2)"
                                           style=""></g>
                                        <g class="highcharts-series highcharts-tracker" visibility="visible" zIndex="10"
                                           transform="translate(72,55) scale(1 1)" clip-path="url(#highcharts-1)"
                                           style=""></g>
                                        <g class="highcharts-markers highcharts-tracker" visibility="visible"
                                           zIndex="10" transform="translate(72,55) scale(1 1)"
                                           clip-path="url(#highcharts-2)" style="">
                                            <path fill="#CCC"
                                                  d="M 497 53.499999999999716 C 502.328 53.499999999999716 502.328 61.499999999999716 497 61.499999999999716 C 491.672 61.499999999999716 491.672 53.499999999999716 497 53.499999999999716 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 434 59.250000000000114 C 439.328 59.250000000000114 439.328 67.25000000000011 434 67.25000000000011 C 428.672 67.25000000000011 428.672 59.250000000000114 434 59.250000000000114 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 372 174.2499999999996 C 377.328 174.2499999999996 377.328 182.2499999999996 372 182.2499999999996 C 366.672 182.2499999999996 366.672 174.2499999999996 372 174.2499999999996 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 310 59.250000000000114 C 315.328 59.250000000000114 315.328 67.25000000000011 310 67.25000000000011 C 304.672 67.25000000000011 304.672 59.250000000000114 310 59.250000000000114 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 248 70.74999999999986 C 253.328 70.74999999999986 253.328 78.74999999999986 248 78.74999999999986 C 242.672 78.74999999999986 242.672 70.74999999999986 248 70.74999999999986 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 186 64.99999999999997 C 191.328 64.99999999999997 191.328 72.99999999999997 186 72.99999999999997 C 180.672 72.99999999999997 180.672 64.99999999999997 186 64.99999999999997 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 124 76.49999999999974 C 129.328 76.49999999999974 129.328 84.49999999999974 124 84.49999999999974 C 118.672 84.49999999999974 118.672 76.49999999999974 124 76.49999999999974 Z"></path>
                                            <path fill="#CCC"
                                                  d="M 62 70.74999999999986 C 67.328 70.74999999999986 67.328 78.74999999999986 62 78.74999999999986 C 56.672 78.74999999999986 56.672 70.74999999999986 62 70.74999999999986 Z"></path>
                                        </g>
                                    </g>
                                    <g class="highcharts-axis-labels highcharts-xaxis-labels" zIndex="7"></g>
                                    <g class="highcharts-axis-labels highcharts-yaxis-labels" zIndex="7">
                                        <text x="57"
                                              style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:183px;text-overflow:clip;"
                                              text-anchor="end" transform="translate(0,0)" y="289" opacity="1">9.2
                                        </text>
                                        <text x="57"
                                              style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:183px;text-overflow:clip;"
                                              text-anchor="end" transform="translate(0,0)" y="231" opacity="1">9.4
                                        </text>
                                        <text x="57"
                                              style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:183px;text-overflow:clip;"
                                              text-anchor="end" transform="translate(0,0)" y="174" opacity="1">9.6
                                        </text>
                                        <text x="57"
                                              style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:183px;text-overflow:clip;"
                                              text-anchor="end" transform="translate(0,0)" y="116" opacity="1">9.8
                                        </text>
                                        <text x="57"
                                              style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:183px;text-overflow:clip;"
                                              text-anchor="end" transform="translate(0,0)" y="59" opacity="1">10
                                        </text>
                                    </g>
                                    <g class="highcharts-tooltip" zIndex="8"
                                       style="cursor:default;padding:0;white-space:nowrap;"
                                       transform="translate(0,-9999)">
                                        <path fill="none"
                                              d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
                                              isShadow="true" stroke="black" stroke-opacity="0.049999999999999996"
                                              stroke-width="5" transform="translate(1, 1)"></path>
                                        <path fill="none"
                                              d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
                                              isShadow="true" stroke="black" stroke-opacity="0.09999999999999999"
                                              stroke-width="3" transform="translate(1, 1)"></path>
                                        <path fill="none"
                                              d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
                                              isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1"
                                              transform="translate(1, 1)"></path>
                                        <path fill="rgba(249, 249, 249, .85)"
                                              d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"></path>
                                    </g>
                                    <text x="574" text-anchor="end" zIndex="8"
                                          style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="295">
                                        <tspan>'The Boys' ratings - www.tvtime.com</tspan>
                                    </text>
                                </svg>
                                <div class="highcharts-tooltip" style="position: absolute; left: 0px; top: -9999px;">
                                    <span style="position: absolute; font-family: helvetica_lt_condensed_medium, sans-serif; white-space: nowrap; font-size: 12px; color: rgb(51, 51, 51); margin-left: 0px; margin-top: 0px; left: 8px; top: 8px;"
                                          zindex="1"></span></div>
                                <span style="position: absolute; font-family: helvetica_lt_condensed_medium, sans-serif; white-space: nowrap; color: rgb(51, 51, 51); font-size: 18px; margin-left: 0px; margin-top: 0px; left: 0px; top: -1px;"
                                      class="highcharts-title" zindex="4" transform="translate(0,0)"><h1 class="title">Show Ratings</h1></span>
                            </div>
                        </div>
                        <div class="row show-nav">
                            <div class="col-lg-7">
                                <div class="basic-infos">
                                    <span>Friday at <time datetime="04:00">04:00</time></span>
                                    <span class="separator">•</span>
                                    <span>Amazon</span>
                                    <span class="separator">•</span>
                                    <span>
            1 seasons          </span>
                                    <span class="separator">•</span>
                                    <span>Still Running</span>
                                </div>
                                <div class="overview">
                                    The Boys is a 60 minute action-adventure-science-fiction starring Karl Urban as
                                    Billy Butcher "El Carnicero", Jack Quaid as Hughie Campbell and Antony Starr as
                                    Homelander. The series was released Fri Jul 26, 2019 on Amazon and is in its first
                                    season.
                                </div>
                                <div class="followers">
                                    233,788 people follow this show
                                </div>
                            </div>
                            <div class="col-lg-1"></div>
                            <div class="col-lg-4">
                                <div class="progression-setup  transparent">
                                    <label>Set up your progression:</label>
                                    <select class="progression-setup-select select2-hidden-accessible" tabindex="-1"
                                            aria-hidden="true">
                                        <option value="1-0" alt="I haven't started this show yet">I haven't started this
                                            show yet
                                        </option>
                                        <option value="1-8" alt="The last aired (I'm up to date)">The last aired (I'm up
                                            to date)
                                        </option>
                                        <optgroup label="Season 1">
                                            <option value="1-1" alt="S01E01">
                                                Episode 1
                                            </option>
                                            <option value="1-2" alt="S01E02">
                                                Episode 2
                                            </option>
                                            <option value="1-3" alt="S01E03">
                                                Episode 3
                                            </option>
                                            <option value="1-4" alt="S01E04">
                                                Episode 4
                                            </option>
                                            <option value="1-5" alt="S01E05">
                                                Episode 5
                                            </option>
                                            <option value="1-6" alt="S01E06">
                                                Episode 6
                                            </option>
                                            <option value="1-7" alt="S01E07">
                                                Episode 7
                                            </option>
                                            <option value="1-8" alt="S01E08">
                                                Episode 8
                                            </option>
                                        </optgroup>
                                    </select><span class="select2 select2-container select2-container--default"
                                                   dir="ltr" style="width: auto;"><span class="selection"><span
                                        class="select2-selection select2-selection--single" role="combobox"
                                        aria-haspopup="true" aria-expanded="false" tabindex="0"
                                        aria-labelledby="select2-84ot-container"><span
                                        class="select2-selection__rendered" id="select2-84ot-container"
                                        title="I haven't started this show yet">I haven't started this show yet</span><span
                                        class="select2-selection__arrow" role="presentation"><b role="presentation"></b></span></span></span><span
                                        class="dropdown-wrapper" aria-hidden="true"></span></span>
                                </div>
                            </div>
                        </div>
                        <div id="tracking-support" data-is-mobile=""
                             data-tracking-link="https://tvtime.onelink.me/3966595826?c=shared_show&amp;pid=tvshowtimeweb"
                             data-deep-link="https://tvtime.onelink.me/3966595826?c=shared_show&amp;pid=tvshowtimeweb&amp;af_dp=tvst://show/355567&amp;af_web_dp=tvst://show/355567"></div>
                        <div id="content" class="show-main alt">
                            <div class="content-container">
                                <div class="left">
                                    <div id="seasons">
                                        <div class="row">
                                            <h2 id="seasons-title" class="title col-lg-3"><i class="fa fa-list"></i>Episodes
                                            </h2>
                                            <div class="col-lg-9">
                                                <div class="filters">
                                                    <div class="dropdown">
                                                        <a id="dSeasons" class="current dropdown-toggle"
                                                           data-toggle="dropdown" href="#" aria-haspopup="true"
                                                           aria-expanded="false">
						      <span class="current-label">
						        Season 1						      </span>
                                                            <i class="icon-tvst-arrow_down"></i>
                                                        </a>
                                                        <ul class="dropdown-menu" role="menu"
                                                            aria-labelledby="dSeasons">
                                                            <li class="top active">
                                                                <a href="#" data-season="1">
                                                                    <i class="icon-tvst-check-small"></i>
                                                                    Season 1 </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="show-seasons">

                                            <div class="seasons">

                                                <div id="season1-content" class="row season-content active" itemscope=""
                                                     itemprop="season" itemtype="http://schema.org/TVSeason">
                                                    <span class="hide" itemprop="name">Season 1</span>
                                                    <span class="hide" itemprop="numberOfEpisodes">8</span>
                                                    <meta itemprop="startDate" content="2019-07-26">
                                                    <div class="col-lg-3 season-img">
                                                        <div class="image-crop">
                                                            <img src="//d36rlb2fgh8cjd.cloudfront.net/default-images/web/poster-340x500/1.png"
                                                                 alt="The Boys">
                                                            <a href="#" class="season-watched-btn watched-btn btn "
                                                               data-season="1">
                                                                <i class="to-watch-icon icon-tvst-watch"></i>
                                                                <i class="watched-icon icon-tvst-check"></i>
                                                                <!-- <div class="watched-label">Season watched</div> -->
                                                                <div class="not-watched-label">Mark season as watched
                                                                </div>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <ul class="list-unstyled episode-list col-lg-9">
                                                        <li id="episode-item-7140390" class="row episode-wrapper odd">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7140390"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                1              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7140390"
                                                                       class="col-sm-6">
              <span class="episode-name">
                The Name of the Game              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7140390"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299045" class="row episode-wrapper">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299045"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                2              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299045"
                                                                       class="col-sm-6">
              <span class="episode-name">
                Cherry              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299045"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299046" class="row episode-wrapper odd">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299046"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                3              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299046"
                                                                       class="col-sm-6">
              <span class="episode-name">
                Get Some              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299046"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299047" class="row episode-wrapper">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299047"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                4              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299047"
                                                                       class="col-sm-6">
              <span class="episode-name">
                The Female of the Species              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299047"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299048" class="row episode-wrapper odd">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299048"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                5              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299048"
                                                                       class="col-sm-6">
              <span class="episode-name">
                Good for the Soul              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299048"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299049" class="row episode-wrapper">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299049"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                6              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299049"
                                                                       class="col-sm-6">
              <span class="episode-name">
                The Innocents              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299049"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299050" class="row episode-wrapper odd">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299050"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                7              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299050"
                                                                       class="col-sm-6">
              <span class="episode-name">
                The Self-Preservation Society               </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299050"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                        <li id="episode-item-7299052" class="row episode-wrapper">
                                                            <div class="col-sm-10 infos">
                                                                <div class="row">
                                                                    <a href="/en/show/355567/episode/7299052"
                                                                       class="col-sm-1">
              <span class="episode-nb-label">
                8              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299052"
                                                                       class="col-sm-6">
              <span class="episode-name">
                You Found Me              </span>
                                                                    </a>
                                                                    <a href="/en/show/355567/episode/7299052"
                                                                       class="col-sm-4">
              <span class="episode-air-date">
                                  2019-07-26                              </span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                            <div class="actions col-sm-2">
                                                                <div class="row">
                                                                    <a href="#"
                                                                       class="col-sm-6 col-sm-offset-4 watched-btn "
                                                                       alt="mark watched">
                                                                        <i class="icon-tvst-watch"></i>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </li> <!-- // .episode-wrapper -->
                                                    </ul>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <!-- Comments -->
                                    <div id="discussion">
                                        <h2 id="community-title" class="title"><i class="fa fa-smile-o"></i>Discussion
                                        </h2>
                                        <div id="show-comments">

                                            <div class="comments">
                                                <div class="new-comment comment">
                                                    <div class="disclaimer">
                                                        <p class="disclaimer-title"><i
                                                                class="fa fa-exclamation-circle"></i> Do not spoil here
                                                        </p>
                                                        <p class="disclaimer-text">On this page, you must only post
                                                            things that will help other users decide if they should
                                                            start the show or not. To talk about specific episodes, go
                                                            on the episode page.</p>
                                                    </div>
                                                    <form class="post" data-parsley-validate="" novalidate="">
                                                        <div class="top">
                                                            <div class="holder mode-comment mode">
                                                                <div class="comment-mode mode">
                                                                    <div class="textarea-wrapper">
                                                                        <div class="mentions-input-box">
                                                                            <div class="mentions">
                                                                                <div style="font-weight: 400; font-family: ProximaNova; font-size: 14px; line-height: 20px;"></div>
                                                                            </div>
                                                                            <textarea
                                                                                    placeholder="Enter your comment here"
                                                                                    data-parsley-minlength="50"
                                                                                    data-parsley-minlength-message="Come on! You need to enter at least a 50 characters comment.."
                                                                                    data-parsley-id="9457"
                                                                                    style="overflow: hidden; height: 40px;"></textarea>
                                                                            <div style="position: absolute; display: none; overflow-wrap: break-word; white-space: pre-wrap; border-color: rgb(85, 85, 85); border-style: none; border-width: 0px; font-weight: 400; width: 201px; font-family: ProximaNova; line-height: 20px; font-size: 14px; padding: 5px;">
                                                                                &nbsp;
                                                                            </div>
                                                                            <div class="simple-autocompleter">
                                                                                <ul class="hidden"></ul>
                                                                            </div>
                                                                        </div>
                                                                        <ul class="parsley-errors-list"
                                                                            id="parsley-id-9457"></ul>
                                                                    </div>
                                                                </div>
                                                                <div class="create-comment-loader">Please wait...</div>
                                                            </div>
                                                            <div class="preview-comment sub-comment">
                                                                <a href="#" class="remove-attached close">×</a>
                                                                <div class="attached-media"></div>
                                                            </div>
                                                            <div class="author">
                                                                <a href="/en/user/15629037/profile">
                                                                    <img class="author-picture img-circle"
                                                                         src="https://d1zfszn0v5ya99.cloudfront.net/user/15629037/profile_picture/5d0d499d718d5_square.png"
                                                                         alt="agusosimani">
                                                                </a>
                                                            </div>
                                                        </div>
                                                        <div class="submit-comment">
                                                            <button type="button" class="submit-comment-btn">Post
                                                            </button>
                                                        </div>
                                                    </form>
                                                    <div class="filters-container">
                                                        <div class="filters sort">
                                                            <div class="dropdown">
                                                                <a id="dComments" class="current dropdown-toggle"
                                                                   data-toggle="dropdown" href="#" aria-haspopup="true"
                                                                   aria-expanded="false">
			    	<span class="current-label">
			    		                            Top comments                        			    	</span>
                                                                    <i class="icon-tvst-arrow_down"></i>
                                                                </a>
                                                                <ul class="dropdown-menu" role="menu"
                                                                    aria-labelledby="dComments">
                                                                    <li class="top active"><a href="#"><i
                                                                            class="icon-tvst-check-small"></i>Top
                                                                        comments</a></li>
                                                                    <li class="last "><a href="#"><i
                                                                            class="icon-tvst-check-small"></i>Last
                                                                        comments</a></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="comments-list">
                                                    <section id="comment-item-1277731"
                                                             class="comment clearfix extended   ">
                                                        <article class="post">
                                                            <div class="top">
                                                                <div class="author">
                                                                    <a href="">
                                                                        <img class="author-picture img-circle"
                                                                             src="https://d1zfszn0v5ya99.cloudfront.net/user/4373065/profile_picture/56da3b6b7237c_square.png"
                                                                             alt="EDUarduuh™">
                                                                    </a>
                                                                </div>
                                                                <div class="remove-zone">
                                                                    <a class="report-btn popover-link" href="#"
                                                                       data-toggle="tooltip" data-placement="right"
                                                                       title="" rel="popover"
                                                                       data-original-title="Report"><i
                                                                            class="icon-tvst-flag"></i></a>
                                                                </div>
                                                                <div class="holder">
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="4373065"
                                                                           data-original-title=""
                                                                           title="">EDUarduuh™</a>
                                                                        <div class="author-level">
                                                                            <i class="icon-tvst-star"></i>
                                                                            <i class="icon-tvst-star"></i>
                                                                            <i class="icon-tvst-star"></i>
                                                                        </div>
                                                                    </div>
                                                                    <div class="comment-date">2 months ago</div>
                                                                    <blockquote class="original">
                                                                        <p>Really Fantastic Show!
                                                                            <br>I believe that if humans get superpower
                                                                            they will definitely kill, not save people.
                                                                        </p>

                                                                        <p>(Already renewed for season 2 😃)</p>
                                                                    </blockquote>
                                                                    <!--
                                                                        <blockquote class="translation">
                                                                            <p>Really Fantastic Show!
                                                <br/>I believe that if humans get superpower they will definitely kill, not save people.</p>

                                                <p>(Already renewed for season 2 😃)</p>
                                                                        </blockquote>
                                                                        <a href="#" class="translation-link">See translation</a>
                                                                     -->
                                                                </div>
                                                            </div>
                                                            <div class="options row">
                                                                <div class="col-lg-7 left-options">
                                                                </div>
                                                                <div class="col-lg-5 right-options">
				<span class="reply-link no-replies">
					<i class="icon-tvst-reply"></i>
						4 replies
				</span>
                                                                    <span class="like-option ">
					<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
					<a href="#" class="like-label">169</a>
				</span>
                                                                </div>
                                                            </div>
                                                        </article>
                                                        <div class="replies sub-comment">
                                                            <article id="comment-1277731-reply-1277761"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/30071689/profile_picture/5ceaff657e5ad_square.png"
                                                                                 alt="Sassy Jacksun">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="30071689"
                                                                           data-original-title="" title="">Sassy
                                                                            Jacksun</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>Season 2: Yasss!! 😁</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">9</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <article id="comment-1277731-reply-1277812"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/15277600/profile_picture/59b5b225b16bb_square.png"
                                                                                 alt="BurningFirebird">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="15277600"
                                                                           data-original-title="" title="">BurningFirebird</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>Exactly!</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">4</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <article id="comment-1277731-reply-1277899"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/20202868/profile_picture/5d633e605c3e5_square.png"
                                                                                 alt="BléuBlane🇫🇷">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="20202868"
                                                                           data-original-title=""
                                                                           title="">BléuBlane🇫🇷</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>So true...I'm waiting to see how they'll take
                                                                            Homekander dwn</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">2</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <article id="comment-1277731-reply-1298060"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d36rlb2fgh8cjd.cloudfront.net/default-images/default-user-q80.png"
                                                                                 alt="Tjdor">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="28374253"
                                                                           data-original-title="" title="">Tjdor</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>Increíble!!!!</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">0</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <form class="reply clearfix">
                                                                <div class="holder">
                                                                    <div class="author">
                                                                        <a href="/en/user/15629037/profile">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/15629037/profile_picture/5d0d499d718d5_square.png"
                                                                                 alt="agusosimani">
                                                                        </a>
                                                                    </div>
                                                                    <div class="textarea-wrapper">
                                                                        <div class="mentions-input-box">
                                                                            <div class="mentions">
                                                                                <div style="font-weight: 400; font-family: ProximaNova; font-size: 14px; line-height: 25px;"></div>
                                                                            </div>
                                                                            <textarea rows="1"
                                                                                      placeholder="Enter your reply here"
                                                                                      style="overflow: hidden; height: 50px;"></textarea>
                                                                            <div style="position: absolute; display: none; overflow-wrap: break-word; white-space: pre-wrap; border-color: rgb(153, 153, 153); border-style: none; border-width: 0px; font-weight: 400; width: 83px; font-family: ProximaNova; line-height: 25px; font-size: 14px; padding: 0px;">
                                                                                &nbsp;
                                                                            </div>
                                                                            <div class="simple-autocompleter">
                                                                                <ul class="hidden"></ul>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </section>
                                                    <section id="comment-item-1276603"
                                                             class="comment clearfix extended   ">
                                                        <article class="post">
                                                            <div class="top">
                                                                <div class="author">
                                                                    <a href="">
                                                                        <img class="author-picture img-circle"
                                                                             src="https://d1zfszn0v5ya99.cloudfront.net/user/1847929/profile_picture/5d8a1b4a6faaf_square.png"
                                                                             alt="duda">
                                                                    </a>
                                                                </div>
                                                                <div class="remove-zone">
                                                                    <a class="report-btn popover-link" href="#"
                                                                       data-toggle="tooltip" data-placement="right"
                                                                       title="" rel="popover"
                                                                       data-original-title="Report"><i
                                                                            class="icon-tvst-flag"></i></a>
                                                                </div>
                                                                <div class="holder">
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="1847929"
                                                                           data-original-title="" title="">duda</a>
                                                                        <div class="author-level">
                                                                            <i class="icon-tvst-star"></i>
                                                                            <i class="icon-tvst-star"></i>
                                                                            <i class="icon-tvst-star"></i>
                                                                        </div>
                                                                    </div>
                                                                    <div class="comment-date">2 months ago</div>
                                                                    <blockquote>
                                                                        <p>What a fucking great season. The pilot is
                                                                            more than enough to keep you hooked and you
                                                                            can barely see the time pass as you watch
                                                                            the eight episodes. I hope it gets the hype
                                                                            and the recognition it deserves. </p>

                                                                        <p>PS: I’m so glad we already have a second
                                                                            season guaranteed.
                                                                            <br>PS2: Shoutout to the soundtrack.
                                                                            Amazing.</p>
                                                                    </blockquote>
                                                                    <!--  -->
                                                                </div>
                                                            </div>
                                                            <div class="options row">
                                                                <div class="col-lg-7 left-options">
                                                                </div>
                                                                <div class="col-lg-5 right-options">
				<span class="reply-link no-replies">
					<i class="icon-tvst-reply"></i>
						3 replies
				</span>
                                                                    <span class="like-option ">
					<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
					<a href="#" class="like-label">93</a>
				</span>
                                                                </div>
                                                            </div>
                                                        </article>
                                                        <div class="replies sub-comment">
                                                            <article id="comment-1276603-reply-1276696"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/18815355/profile_picture/5a7d9e6e3fe8f_square.png"
                                                                                 alt="Thunderbirds">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="18815355"
                                                                           data-original-title=""
                                                                           title="">Thunderbirds</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>Agree with everything except the soundtrack
                                                                            part. They could have gotten different songs
                                                                            eg when Billy is going to club translucent
                                                                            with crowbar, would have like The Angels
                                                                            "take long line". Try and play the scene and
                                                                            turn down the volume and play that
                                                                            song...would have worked so much better.</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">0</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <article id="comment-1276603-reply-1276819"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/25628779/profile_picture/5c1658f604a4c_square.png"
                                                                                 alt="TalynStarburst">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="25628779"
                                                                           data-original-title="" title="">TalynStarburst</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>I’m looking forward to watching it!</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">1</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <article id="comment-1276603-reply-1278830"
                                                                     class="reply clearfix initialized">
                                                                <div class="holder">
                                                                    <div class="top">
                                                                    </div>
                                                                    <div class="author">
                                                                        <a href="">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/11859681/profile_picture/5d400f7537dec_square.png"
                                                                                 alt="Paulo Alexandre">
                                                                        </a>
                                                                    </div>
                                                                    <div class="author-label">
                                                                        <a href="" class="data-popover user-popover"
                                                                           data-toggle="popover" data-id="11859681"
                                                                           data-original-title="" title="">Paulo
                                                                            Alexandre</a>
                                                                    </div>
                                                                    <blockquote>
                                                                        <p>Série magnífica</p>
                                                                    </blockquote>
                                                                    <div class="options ">
						<span class="like-option">
							<a href="#" class="like-btn"><i class="icon-tvst-genre-drama"></i></a>
							<a href="#" class="like-label">0</a>
						</span>
                                                                        <a class="report-btn popover-link" href="#"
                                                                           data-toggle="tooltip" data-placement="right"
                                                                           title="" rel="popover"
                                                                           data-original-title="Report"><i
                                                                                class="icon-tvst-flag"></i></a>
                                                                    </div>
                                                                </div>
                                                            </article>
                                                            <form class="reply clearfix">
                                                                <div class="holder">
                                                                    <div class="author">
                                                                        <a href="/en/user/15629037/profile">
                                                                            <img class="author-picture"
                                                                                 src="https://d1zfszn0v5ya99.cloudfront.net/user/15629037/profile_picture/5d0d499d718d5_square.png"
                                                                                 alt="agusosimani">
                                                                        </a>
                                                                    </div>
                                                                    <div class="textarea-wrapper">
                                                                        <div class="mentions-input-box">
                                                                            <div class="mentions">
                                                                                <div style="font-weight: 400; font-family: ProximaNova; font-size: 14px; line-height: 25px;"></div>
                                                                            </div>
                                                                            <textarea rows="1"
                                                                                      placeholder="Enter your reply here"
                                                                                      style="overflow: hidden; height: 50px;"></textarea>
                                                                            <div style="position: absolute; display: none; overflow-wrap: break-word; white-space: pre-wrap; border-color: rgb(153, 153, 153); border-style: none; border-width: 0px; font-weight: 400; width: 83px; font-family: ProximaNova; line-height: 25px; font-size: 14px; padding: 0px;">
                                                                                &nbsp;
                                                                            </div>
                                                                            <div class="simple-autocompleter">
                                                                                <ul class="hidden"></ul>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div> <!-- //.col -->
                                <div class="right">
                                    <section>
                                        <h1 class="title all-actors">Favorite characters</h1>
                                        <a class="all-actors-link" href="#">See all</a>
                                        <div id="show-casting" class="cast cast-ranking">
                                            <ul class="actors-list clearfix list-unstyled list-inline">
                                                <li class="actor-item popular" itemprop="actor" itemscope=""
                                                    itemtype="http://schema.org/Person">
                                                    <a href="/en/actor/47398">
                                                        <div class="image-crop">
                                                            <img itemprop="image"
                                                                 src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524144_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Annie January / S...</h3>
                                                            <span itemprop="name">Erin Moriarty</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        53465 votes
                                                    </div>
                                                    <div class="rank-sticker rank-1">1</div>
                                                </li>
                                                <li class="actor-item popular" itemprop="actor" itemscope=""
                                                    itemtype="http://schema.org/Person">
                                                    <a href="/en/actor/11778">
                                                        <div class="image-crop">
                                                            <img itemprop="image"
                                                                 src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524159_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Frenchie</h3>
                                                            <span itemprop="name">Tomer Kapon</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        34696 votes
                                                    </div>
                                                    <div class="rank-sticker rank-2">2</div>
                                                </li>
                                                <li class="actor-item popular" itemprop="actor" itemscope=""
                                                    itemtype="http://schema.org/Person">
                                                    <a href="/en/actor/4901">
                                                        <div class="image-crop">
                                                            <img itemprop="image"
                                                                 src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/551656_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Billy Butcher "El...</h3>
                                                            <span itemprop="name">Karl Urban</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        25292 votes
                                                    </div>
                                                    <div class="rank-sticker rank-3">3</div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/73580">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/551655_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Hughie Campbell</h3>
                                                            <span>Jack Quaid</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        17763 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/576">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524160_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Hugh Campbell Sr.</h3>
                                                            <span>Simon Pegg</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        11376 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/17879">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/551654_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Homelander</h3>
                                                            <span>Antony Starr</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        6473 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/127334">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/550993_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>The Female</h3>
                                                            <span>Karen Fukuhara</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        3733 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/44337">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524148_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Queen Maeve</h3>
                                                            <span>Dominique McElligott</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        2123 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/28897">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524152_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>The Deep</h3>
                                                            <span>Chace Crawford</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        1199 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/40559">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/550992_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Mother's Milk</h3>
                                                            <span>Laz Alonso</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        1164 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/135278">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524154_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Black Noir</h3>
                                                            <span>Nathan Mitchell</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        188 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/17568">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524143_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Madelyn Stillwell</h3>
                                                            <span>Elisabeth Shue</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        177 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/47008">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/524150_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>A-Train</h3>
                                                            <span>Jessie Usher</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        67 votes
                                                    </div>
                                                </li>
                                                <li class="actor-item other">
                                                    <a href="/en/actor/18201">
                                                        <div class="image-crop">
                                                            <img src="https://dg31sz3gwrwan.cloudfront.net/actor/355567/560647_medium-optimized-2.jpg"
                                                                 alt="image description" width="160">
                                                        </div>
                                                        <div class="content">
                                                            <h3>Becca Butcher</h3>
                                                            <span>Shantel VanSanten</span>
                                                        </div>
                                                    </a>
                                                    <div class="nb-votes">
                                                        0 vote
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </section>
                                </div><!-- //.col -->
                            </div>
                        </div> <!-- //#content -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
