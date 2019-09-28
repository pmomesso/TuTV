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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">

    <script>document.getElementsByTagName("html")[0].className += " js";</script>
    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/util.js"/>"></script>
    <script src="<c:url value="/resources/js/main.js"/>"></script>
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
                <div id="explore">
                    <section id="new-shows">
                        <h1>${serie.name}</h1>
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="${serie.bannerUrl}" itemprop="image" alt="${serie.bannerUrl}">
                                    <div class="carousel-caption">
                                        <div>
                                            <label class="star"/>
                                            <h2 class="float-right rating">${serie.userRating}/5</h2>
                                        </div>
<%--                                        <div class="container h-20">--%>
<%--                                            <div class="starrating risingstar d-flex justify-content-center flex-row-reverse">--%>
<%--                                                <input type="radio" id="star5" name="rating" value="5" /><label for="star5" title="5 star"></label>--%>
<%--                                                <input type="radio" id="star4" name="rating" value="4" /><label for="star4" title="4 star"></label>--%>
<%--                                                <input type="radio" id="star3" name="rating" value="3" /><label for="star3" title="3 star"></label>--%>
<%--                                                <input type="radio" id="star2" name="rating" value="2" /><label for="star2" title="2 star"></label>--%>
<%--                                                <input type="radio" id="star1" name="rating" value="1" /><label for="star1" title="1 star"></label>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
                                        <button class="add-button">Add</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                </section>
                    <div class="main-block-container">
                    <div id="show-details" class="show" itemscope="" itemtype="http://schema.org/TVSeries">
                        <div class="row show-nav">
                            <div class="col-lg-7">
                                <div class="basic-infos">
                                    <span>Friday at <time datetime="04:00">04:00</time></span>
                                    <span class="separator">•</span>
                                    <span>${serie.network}</span>
                                    <span class="separator">•</span>
                                    <span>
            1 seasons          </span>
                                    <span class="separator">•</span>
                                    <span>Still Running</span>
                                </div>
                                <div class="overview">
                                    ${serie.seriesDescription}
                                </div>
                                <div class="followers">
                                    ${serie.numFollowers} <spring:message code="index.followers"/>
                                </div>
                            </div>
                            <div class="col-lg-1"></div>
                            <div class="col-lg-4"></div>
                        </div>
                        <div id="content" class="show-main alt">
                            <div class="content-container">
                                <div class="left">
                                    <div class="container max-width-sm no-padding no-margin">
                                        <ul class="cd-accordion cd-accordion--animated">
                                            <c:forEach items="${serie.seasons}" var="season" varStatus="item">
                                                <li class="cd-accordion__item cd-accordion__item--has-children">
                                                    <input class="cd-accordion__input" type="checkbox" name ="group-${item.index}" id="group-${item.index}">
                                                    <label class="cd-accordion__label cd-accordion__label--icon-folder drop" for="group-${item.index}"><span class="big-size">Season ${season.seasonNumber}</span></label>

                                                    <ul class="cd-accordion__sub cd-accordion__sub--l1">
                                                        <c:forEach items="${season.episodeList}" var="episode">
<%--                                                            TODO tener el id del episodio en vez del primer episodeNumber--%>
                                                            <li class="cd-accordion__item">
                                                                <h3>${episode.episodeNumber} - ${episode.name}</h3>
<%--                                                                TODO este a deberia llevar a un post de chequear elemento..--%>
                                                                <a class="cd-accordion__label cd-accordion__label--icon-img" href="/episode?id=${episode.episodeNumber}">
                                                                <c:if test="${not empty user}">
                                                                    <c:choose>
                                                                        <c:when test="${episode.viewed}">
                                                                            <span style="font-family: FontAwesome; font-style: normal" class="check viewed">&#xf058</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span style="font-family: FontAwesome; font-style: normal" class="check">&#xf058</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:if>
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
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
                            </div>
                        </div> <!-- //#content -->
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
