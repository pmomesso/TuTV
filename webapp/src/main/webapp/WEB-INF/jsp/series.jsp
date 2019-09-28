<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
            <div class="main-block">
                <div id="explore">
                    <section id="new-shows">
                        <h1>${series.name}</h1>
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="${series.bannerUrl}" itemprop="image" alt="${series.bannerUrl}">
                                    <div class="carousel-caption">
                                        <div class="text-center">
                                            <span class="star"></span>
                                            <h2>${series.userRating}/5</h2>
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
                                        <form action="<c:url value="/addSeries?seriesId=${series.id}&userId=${user.id}"/>" method="post">
                                            <button class="add-button" type="submit">Add</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                </section>
                    <div class="main-block-container">
                    <div id="show-details" class="show">
                        <div class="row show-nav">
                            <div class="col-lg-7">
                                <div class="basic-infos">
                                    <span>${series.network}</span>
                                    <span class="separator">•</span>
                                    <span>${fn:length(series.seasons)} <spring:message code="series.season"/><c:if test="${fn:length(series.seasons) gt 1}">s</c:if></span>
                                </div>
                                <div class="overview">
                                    ${series.seriesDescription}
                                </div>
                                <div class="followers">
                                    ${series.numFollowers} <spring:message code="index.followers"/>
                                </div>
                            </div>
                            <div class="col-lg-1"></div>
                            <div class="col-lg-4"></div>
                        </div>
                        <div id="content" class="show-main alt">
                            <div class="content-container">
                                <div class="left">
                                    <div class="w-100 no-padding no-margin">
                                        <ul class="cd-accordion cd-accordion--animated">
                                            <c:forEach items="${series.seasons}" var="season" varStatus="item">
                                                <li class="cd-accordion__item cd-accordion__item--has-children">
                                                    <input class="cd-accordion__input" type="checkbox" name ="group-${item.index}" id="group-${item.index}">
                                                    <label class="cd-accordion__label cd-accordion__label--icon-folder drop" for="group-${item.index}"><span class="big-size">Season ${season.seasonNumber}</span></label>

                                                    <ul class="cd-accordion__sub cd-accordion__sub--l1">
                                                        <c:forEach items="${season.episodeList}" var="episode">
<%--                                                                TODO este a deberia llevar a un post de chequear elemento..--%>
                                                            <li class="cd-accordion__label cd-accordion__label--icon-img">
                                                                <div class="cd-accordion__item">
                                                                    <h3>${episode.episodeNumber} - ${episode.name}</h3>
                                                                    <c:if test="${not empty user}">
                                                                        <c:choose>
                                                                            <c:when test="${episode.viewed}">
                                                                                <span style="font-family: FontAwesome,serif; font-style: normal" class="check viewed">&#xf058</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <form action="<c:url value="/viewEpisode?seriesId=${series.id}&episodeId=${episode.id}&userId=${user.id}"/>" method="post">
                                                                                    <button type="submit" style="font-family: FontAwesome,serif; font-style: normal" class="check">&#xf058</button>
                                                                                </form>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:if>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <div id="discussion">
                                        <h2 id="community-title" class="title"><spring:message code="series.discussion"/></h2>
                                        <div id="show-comments">
                                            <div class="comments">
                                                <div class="new-comment comment">
                                                    <div class="disclaimer">
                                                        <p class="disclaimer-title"><spring:message code="series.spoil"/></p>
                                                    </div>
                                                    <form:form class="post" modelAttribute="postForm" action="/post" method="post" enctype="application/x-www-form-urlencoded">
                                                        <div class="top">
                                                            <form:errors path="description" element="p" cssClass="error text-left"/>
                                                            <div class="holder mode-comment mode">
                                                                <div class="comment-mode mode">
                                                                    <div class="textarea-wrapper">
                                                                        <div class="mentions-input-box">
                                                                            <form:textarea
                                                                                    placeholder="Enter your comment here"
                                                                                    path="description"
                                                                                    style="overflow: hidden; height: 40px;"/>
                                                                            <form:input type="hidden" path="userId" value="${user.id}"/>
                                                                            <form:input type="hidden" path="seriesId" value="${series.id}"/>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="author">
                                                                <img class="author-picture img-circle"
                                                                     src="https://d1zfszn0v5ya99.cloudfront.net/user/15629037/profile_picture/5d0d499d718d5_square.png"
                                                                     alt="${user.userName}">
                                                            </div>
                                                        </div>
                                                        <div class="submit-comment">
                                                            <button type="submit" class="submit-comment-btn"><spring:message code="series.post"/></button>
                                                        </div>
                                                    </form:form>
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
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
