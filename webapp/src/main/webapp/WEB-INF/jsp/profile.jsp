<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <c:if test="${user.id eq userProfile.id}">
        <script src="<c:url value="/resources/js/profile.js"/>"></script>
    </c:if>
</head>
<body id="container" class="">
<div class="body-inner">
    <%@ include file="sideMenu.jsp" %>
    <div class="page-center page-column">
        <div class="page-center-inner">
            <div class="main-block-container">
                <div id="profile">
                    <div class="images">
                        <div class="images-inner"></div>
                        <img src="<c:url value="/resources/img/background.jpg"/>"/>
                    </div>
                    <div class="profile-nav">
                        <div class="row wrapper">
                            <div class="avatar">
                                <a href="#" class="avatar-upload-link" id="showUploadAvatarPopup">
                                    <c:choose>
                                        <c:when test="${hasAvatar}">
                                            <img src="<c:url value="/user/${user.id}/avatar"/>" alt="avatar">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value="https://d36rlb2fgh8cjd.cloudfront.net/default-images/default-user-q80.png"/>" alt="avatar">
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${user.id == userProfile.id}">
                                        <span class="avatar-upload-label">
                                            <spring:message code="profile.edit"/>
                                        </span>
                                    </c:if>
                                </a>
                            </div>
                            <div class="profile-infos">
                                <h1 class="name">
                                    <c:out value="${userProfile.userName}"/>
                                </h1>
                            </div>
                            <ul class="nav nav-tabs" role="tab">
                                <li id="followedTab" role="presentation" class="tab-shows <c:if test="${!exists && empty formErrors}">active</c:if>">
                                    <a id="followedLink" href="#tab-shows" data-toggle="tab" aria-controls="tab-shows" aria-expanded="true">
                                        <div class="label"><spring:message code="profile.followed"/></div>
                                    </a>
                                </li>
                                <c:if test="${user.id == userProfile.id}">
                                <li id="informationTab" role="presentation" class="tab-shows <c:if test="${exists || not empty formErrors}">active</c:if>">
                                    <a id="informationLink"  href="#tab-information" data-toggle="tab" aria-controls="tab-information" aria-expanded="true">
                                        <div class="label"><spring:message code="profile.information"/></div>
                                    </a>
                                </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <div class="popover fade bottom in" role="tooltip" id="uploadAvatarPopup" style="top: 250px; left: -41px; display: none;">
                        <div class="arrow" style="left: 50%;"></div>
                        <h3 class="popover-title"><spring:message code="profile.upload"/> </h3>
                        <div class="popover-content">
                            <h3 class="popover-title" id="avatarMaxSizeError" style="display: none">
                                <font color="red"><spring:message code="profile.avatarMaxSize"/> 100KB</font>
                            </h3>
                            <form id="avatarFileForm" action="<c:url value="/uploadAvatar"/>" method="post" enctype="multipart/form-data">
                                <input id="avatarFileInput" type="file" name="avatar" data-max-size="100000">
                            </form>
                        </div>
                    </div>
                    <div class="profile-content">
                        <div class="wrapper">
                            <div class="tab-content">
                                <div id="tab-shows" class="tab-pane <c:if test="${!exists && empty formErrors}">active</c:if>" role="tabpanel">
                                    <div id="profile-shows">
                                        <c:if test="${user.id eq userProfile.id && not empty recentlyWatched}">
                                            <div id="recently-watched-shows">
                                                <h2 class="small"><spring:message code="profile.recently"/></h2>
                                                <section>
                                                    <ul class="shows-list posters-list list-unstyled list-inline">
                                                        <c:forEach items="${recentlyWatched}" var="series">
                                                            <li class="first-loaded">
                                                                <div class="show">
                                                                    <a href="<c:url value="/series?id=${series.id}"/>" class="show-link">
                                                                        <div class="image-crop">
                                                                            <img src="<c:url value="https://image.tmdb.org/t/p/original${series.posterUrl}"/>"
                                                                                 alt="${series.name}">
                                                                        </div>
                                                                    </a>
                                                                    <div class="poster-details">
                                                                        <h2><a href="<c:url value="/series?id=${series.id}"/>">${series.name}</a></h2>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </section>
                                            </div>
                                        </c:if>
                                        <div id="all-shows">
                                            <h2 class="small"><spring:message code="profile.all"/></h2>
                                            <section>
                                                <c:choose>
                                                    <c:when test="${not empty followedSeries}">
                                                        <ul class="shows-list posters-list list-unstyled list-inline">
                                                            <c:forEach items="${followedSeries}" var="series">
                                                                <li class="first-loaded">
                                                                    <div class="show">
                                                                        <a href="<c:url value="/series?id=${series.id}"/>" class="show-link">
                                                                            <div class="image-crop">
                                                                                <img src="<c:url value="https://image.tmdb.org/t/p/original${series.posterUrl}"/>"
                                                                                     alt="${series.name}">
                                                                            </div>
                                                                        </a>
                                                                        <div class="poster-details">
                                                                            <h2><a href="<c:url value="/series?id=${series.id}"/>">${series.name}</a></h2>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="container h-100">
                                                            <div class="row justify-content-center h-100">
                                                                <div class="col-lg-8 col-sm-12 align-self-center">
                                                                    <div class="text-center m-4">
                                                                        <h4><spring:message code="watchlist.discover"/></h4>
                                                                    </div>
                                                                    <div class="text-center m-4">
                                                                        <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </section>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-information" class="tab-pane <c:if test="${exists || not empty formErrors}">active</c:if>" role="tabpanel">
                                    <section id="basic-settings" class="container">
                                        <div class="row text-center" style="padding: 50px">
                                            <div class="col my-auto self-align-center">
                                                <div class="other-infos infos-zone">
                                                    <c:url value="/user/update" var="updateUrl"/>
                                                    <form:form modelAttribute="updateUserForm" action="${updateUrl}" method="post" enctype="application/x-www-form-urlencoded">
                                                        <div class="row form-group">
                                                            <form:label class="col-sm-4 control-label" path="username"><spring:message code="register.username"/></form:label>
                                                            <div class="col-sm-6">
                                                                <form:input path="username" type="text" class="form-control" name="username" placeholder="JohnDoe" value="${userProfile.userName}"/>
                                                                <form:errors path="username" element="p" cssClass="m-2 error"/>
                                                                <c:if test="${exists}">
                                                                    <p class="m-2 error"><spring:message code="profile.usernameExists"/></p>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group">
                                                            <label class="col-sm-4 control-label"><spring:message code="register.mail"/></label>
                                                            <div class="col-sm-6">
                                                                <input type="email" class="form-control" name="mail" placeholder="john@doe.com" value="${userProfile.mailAddress}" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group text-center">
                                                            <div class="col">
                                                                <button type="submit" class="submit-comment-btn"><spring:message code="profile.save"/></button>
                                                            </div>
                                                        </div>
                                                    </form:form>
                                                </div>
                                            </div>
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
</body>
</html>
