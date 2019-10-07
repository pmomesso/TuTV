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
    <c:if test="${user.id == userProfile.id}">
        <script src="<c:url value="/resources/js/uploadAvatar.js"/>"></script>
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
                                <li role="presentation" class="tab-shows active">
                                    <a href="#tab-shows" data-toggle="tab" aria-controls="tab-shows" aria-expanded="true">
                                        <div class="label"><spring:message code="profile.followed"/></div>
                                    </a>
                                </li>
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
                </div>
                <div class="profile-content">
                    <div class="wrapper">
                        <div class="tab-content">
                            <div id="tab-shows" class="tab-pane active" role="tabpanel">
                                <div id="profile-shows" style="margin-top: 20px; margin-left: 20px">
                                    <ul class="posters-list shows-list explore-list list-unstyled list-inline" style="overflow: visible">
                                    <c:forEach items="${followedSeries}" var="serie">
                                        <li id="${serie.id}">
                                            <div class="image-crop">
                                                <a href="<c:url value="/series?id=${serie.id}"/>">
                                                    <img src="<c:url value="${serie.posterUrl}"/>"
                                                         alt="${serie.name}">
                                                </a>
                                                <div class="overlay">
                                                    <a href="<c:url value="/series?id=${serie.id}"/>" class="zoom-btn overlay-btn" title="info"></a>
                                                </div>
                                                <div class="side progress-box">
                                                    <div class="loader rotating dark small visible"></div>
                                                </div>
                                            </div>
                                            <div class="show-details poster-details">
                                                <h2><a href="<c:url value="/series?id=${serie.id}"/>">${serie.name}</a></h2>
                                            </div>
                                        </li>
                                    </c:forEach>
                                    </ul>
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
