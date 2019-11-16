<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script src="<c:url value="/resources/js/charts.js"/>"></script>
    <c:if test="${user.id eq userProfile.id}">
        <script src="<c:url value="/resources/js/profile.js"/>"></script>
    </c:if>
</head>
<body id="container" class="">
<!-- Add list Modal -->
<div class="modal" id="addList">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title m-0"><spring:message code="profile.addList"/></h2>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <c:choose>
                <c:when test="${empty followedSeries}">
                    <div class="modal-body container">
                        <div class="row justify-content-center h-100">
                            <div class="col-lg-8 col-sm-12 align-self-center">
                                <div class="text-center m-4">
                                    <h4><spring:message code="profile.noShows"/></h4>
                                </div>
                                <div class="text-center m-4">
                                    <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="profile.close"/></button>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:url value='addList' var="action_value"/>
                    <form:form id="listForm" modelAttribute="listForm" action="${action_value}" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="modal-body container">
                        <div class="row w-100 mb-4">
                            <div class="col-3 h-100 align-self-center">
                                <form:label class="ml-lg-5" path="name"><spring:message code="profile.listName"/></form:label>
                            </div>
                            <div class="col-9 align-self-center">
                                <form:input class="m-3 w-100" path="name" type="text" maxlength="50"/>
                                <form:errors path="name" element="p" cssClass="ml-3 error"/>
                            </div>
                        </div>
                        <c:forEach items="${followedSeries}" var="series">
                            <div class="row w-100 m-0">
                                <label for="seriesId${series.id}" class="font-weight-normal">
                                    <form:checkbox cssClass="ml-5 mr-3" path="seriesId" id="seriesId${series.id}" value="${series.id}"/>${series.name}
                                </label>
                            </div>
                        </c:forEach>
                        <form:input type="hidden" path="userId" value="${userProfile.id}"/>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn tutv-button"><spring:message code="profile.done"/></button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="profile.close"/></button>
                    </div>
                    </form:form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<div class="body-inner">
    <%@ include file="sideMenu.jsp" %>
    <div class="page-center page-column">
        <div class="page-center-inner">
            <div class="main-block-container">
                <div id="profile">
                    <div class="images">
                        <div class="images-inner"></div>
                        <img src="<c:url value="/resources/img/background.jpg"/>" alt="Background"/>
                    </div>
                    <div class="profile-nav">
                        <div class="row wrapper">
                            <div class="avatar">
                                <a href="#" class="avatar-upload-link" id="showUploadAvatarPopup">
                                    <c:choose>
                                        <c:when test="${not empty userProfile.userAvatar}">
                                            <img src="<c:url value="/user/${userProfile.id}/avatar"/>" alt="avatar">
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
                            <ul class="nav nav-tabs align-self-center">
                                <li id="followedTab" role="presentation" class="tab-shows <c:if test="${!exists && empty formErrors}">active</c:if>">
                                    <a id="followedLink" href="#tab-shows" data-toggle="tab" aria-controls="tab-shows" aria-expanded="true">
                                        <div class="label"><spring:message code="profile.followed"/></div>
                                    </a>
                                </li>
                                <c:if test="${user.id == userProfile.id}">
                                <li id="listsTab" role="presentation" class="tab-information">
                                    <a id="listsLink" href="#tab-lists" data-toggle="tab" aria-controls="tab-lists" aria-expanded="true" >
                                        <div class="label"><spring:message code="profile.lists"/></div>
                                    </a>
                                </li>
                                <li id="statsTab" role="presentation" class="tab-information">
                                    <a id="statsLink" href="#tab-stats" data-toggle="tab" aria-controls="tab-stats" aria-expanded="true" >
                                        <div class="label"><spring:message code="profile.stats"/></div>
                                    </a>
                                </li>
                                <li id="informationTab" role="presentation" class="tab-information <c:if test="${exists || not empty formErrors}">active</c:if>">
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
                                <span style="color: red"><spring:message code="profile.avatarMaxSize"/> 2MB</span>
                            </h3>
                            <h3 class="popover-title"  id="wrongFileTypeError" style="display: none">
                                <span style="color: red"><spring:message code="profile.wrongFileType"/></span>
                            </h3>
                            <form id="avatarFileForm" action="<c:url value="/uploadAvatar"/>" method="post" enctype="multipart/form-data">
                                <input id="avatarFileInput" type="file" name="avatar" data-max-size="2097152" accept=".jpg,.jpeg,.png">
                            </form>
                        </div>
                    </div>
                    <div class="profile-content">
                        <div class="wrapper">
                            <div class="tab-content">
                                <div id="tab-shows" class="tab-pane <c:if test="${!exists && empty formErrors}">active</c:if>" role="tabpanel">
                                    <div class="profile-shows">
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
                                                        <c:choose>
                                                            <c:when test="${user.id eq userProfile.id}">
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
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="container h-100">
                                                                    <div class="row justify-content-center h-100">
                                                                        <div class="col-lg-8 col-sm-12 align-self-center">
                                                                            <div class="text-center m-4">
                                                                                <h4><spring:message code="profile.userNoShows" arguments="${userProfile.userName}"/></h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </section>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-lists" class="tab-pane" role="tabpanel">
                                    <c:choose>
                                        <c:when test="${empty userProfile.lists}">
                                            <div id="all-shows">
                                                <h2 class="small"> </h2>
                                                <section>
                                                    <div class="container h-100">
                                                        <div class="row justify-content-center h-100">
                                                            <div class="col-lg-8 col-sm-12 align-self-center">
                                                                <div class="text-center m-4">
                                                                    <h4><spring:message code="profile.noLists"/></h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </section>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${userProfile.lists}" var="list">
                                                <div class="profile-shows">
                                                    <div>
                                                        <div class="overflow-hidden">
                                                            <h2 class="small float-left">${list.name}</h2>
                                                            <a type="button" class="show-link float-left icon-margin" data-toggle="modal" data-target="#modifyList${list.id}" style="text-decoration: none !important; color: black">
                                                                <span style="font-family: FontAwesome,serif; font-style: normal; color: gray">&#xf040</span>
                                                            </a>
                                                            <form action="<c:url value="/removeList?id=${list.id}&userId=${userProfile.id}"/>"
                                                                  method="post" class="icon-margin float-left" onsubmit="confirmAction(event,'<spring:message code="profile.sureRemove" arguments="${list.name}"/>')">
                                                                <button type="submit" class="heart no-padding" style="font-family: FontAwesome,serif; font-style: normal">&#xf1f8</button>
                                                            </form>
                                                        </div>
                                                        <section>
                                                            <ul class="shows-list posters-list list-unstyled list-inline">
                                                                <c:forEach items="${list.series}" var="series">
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
                                                </div>
                                                <!-- Modify list Modal -->
                                                <div class="modal" id="modifyList${list.id}">
                                                    <div class="modal-dialog modal-dialog-centered">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h2 class="modal-title m-0"><spring:message code="profile.modifyList"/></h2>
                                                                <button type="button" class="close" data-dismiss="modal">
                                                                    <span>&times;</span>
                                                                </button>
                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${empty followedSeries}">
                                                                    <div class="modal-body container">
                                                                        <div class="row justify-content-center h-100">
                                                                            <div class="col-lg-8 col-sm-12 align-self-center">
                                                                                <div class="text-center m-4">
                                                                                    <h4><spring:message code="profile.noShows"/></h4>
                                                                                </div>
                                                                                <div class="text-center m-4">
                                                                                    <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="profile.close"/></button>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:url value='modifyList' var="action_value"/>
                                                                    <form:form id="listForm" modelAttribute="listModifyForm" action="${action_value}" method="post" enctype="application/x-www-form-urlencoded">
                                                                        <div class="modal-body container">
                                                                            <div class="row w-100 mb-4">
                                                                                <div class="col-3 h-100 align-self-center">
                                                                                    <form:label class="ml-lg-5" path="name"><spring:message code="profile.listName"/></form:label>
                                                                                </div>
                                                                                <div class="col-9 align-self-center">
                                                                                    <form:input class="m-3 w-100" path="name" type="text" maxlength="50" value="${list.name}"/>
                                                                                    <form:errors path="name" element="p" cssClass="ml-3 error"/>
                                                                                </div>
                                                                            </div>
                                                                            <c:forEach items="${followedSeries}" var="series">
                                                                                <div class="row w-100 m-0">
                                                                                    <label for="seriesId${series.id}modify" class="font-weight-normal">
                                                                                        <c:choose>
                                                                                            <c:when test="${fn:contains(list.series, series)}">
                                                                                                <form:checkbox cssClass="ml-5 mr-3" path="seriesId" id="seriesId${series.id}modify" value="${series.id}" checked="true"/>
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                <form:checkbox cssClass="ml-5 mr-3" path="seriesId" id="seriesId${series.id}modify" value="${series.id}"/>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                        ${series.name}
                                                                                    </label>
                                                                                </div>
                                                                            </c:forEach>
                                                                            <form:input type="hidden" path="id" value="${list.id}"/>
                                                                            <form:input type="hidden" path="userId" value="${userProfile.id}"/>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="submit" class="btn tutv-button"><spring:message code="profile.done"/></button>
                                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="profile.close"/></button>
                                                                        </div>
                                                                    </form:form>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    <div id="all-shows">
                                        <div class="container h-100">
                                            <div class="row justify-content-center h-100">
                                                <div class="col-lg-8 col-sm-12 align-self-center">
                                                    <div class="text-center m-4">
                                                        <button type="button" class="tutv-button" data-toggle="modal" data-target="#addList"><spring:message code="profile.addList"/></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-stats" class="tab-pane" role="tabpanel">
                                    <div class="profile-shows">
                                        <div>
                                            <h2 class="small"><spring:message code="profile.favoriteGenres"/></h2>
                                            <div class="row justify-content-center">
                                                <c:choose>
                                                    <c:when test="${empty genreStats}">
                                                        <div class="container h-100">
                                                            <div class="row justify-content-center h-100">
                                                                <div class="col-lg-8 col-sm-12 align-self-center">
                                                                    <div class="text-center m-4">
                                                                        <h4><spring:message code="profile.noStats"/></h4>
                                                                        <h4><spring:message code="watchlist.discover"/></h4>
                                                                    </div>
                                                                    <div class="text-center m-4">
                                                                        <button class="tutv-button m-4" onclick="window.location.href='<c:url value="/"/>'"><spring:message code="watchlist.explore"/></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="mt-lg-5 mt-sm-0"><canvas id="genresChart" ></canvas></div>
                                                        <c:set var="labels" value="["/>
                                                        <c:forEach var="key" items="${genreStats.keySet()}">
                                                            <c:set var="labels" value="${labels} '${key}',"/>
                                                        </c:forEach>
                                                        <c:set var="labels" value="${labels}]"/>
                                                        <script>
                                                            var ctx = document.getElementById('genresChart');
                                                            var myChart = new Chart(ctx, {
                                                                type: 'doughnut',
                                                                data: {
                                                                    labels: ${labels},
                                                                    datasets: [{
                                                                        data: ${genreStats.values()},
                                                                        backgroundColor: [
                                                                            '#3cb44b', '#469990', '#aaffc3', '#42d4f4', '#4363d8',
                                                                            '#000075', '#911eb4', '#f032e6', '#e6beff', '#800000',
                                                                            '#e6194b', '#f58231', '#ffd8b1', '#ffe119', '#bfef45'
                                                                        ]
                                                                    }]
                                                                },
                                                                options: {
                                                                    maintainAspectRatio: false,
                                                                    legend: {
                                                                        display: true,
                                                                        position: 'bottom',
                                                                        labels: {
                                                                            padding: 20
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        </script>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
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
                                                                <form:input path="username" type="text"  minlength="6" maxlength="32" class="form-control" name="username" placeholder="JohnDoe" value="${userProfile.userName}"/>
                                                                <form:errors path="username" element="p" cssClass="m-2 error"/>
                                                                <c:if test="${exists}">
                                                                    <p class="m-2 error"><spring:message code="profile.usernameExists"/></p>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group">
                                                            <label class="col-sm-4 control-label"><spring:message code="register.mail"/></label>
                                                            <div class="col-sm-6">
                                                                <input type="email" class="form-control" name="mail" value="${userProfile.mailAddress}" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="row text-center justify-content-center">
                                                            <div class="col align-self-center">
                                                                <div class="text-center m-4">
                                                                    <button type="submit" class="tutv-button m-4" ><spring:message code="profile.save"/></button>
                                                                </div>
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
