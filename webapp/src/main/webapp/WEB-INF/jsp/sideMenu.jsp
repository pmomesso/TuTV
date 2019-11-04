<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<div class="page-left page-sidebar page-column">
    <a href="#" class="extend-left-link"><span style="font-family: FontAwesome,serif; font-style: normal" onclick="extend()">&#xf0c9</span></a>
    <div class="scrollable scrolling-element">
        <div class="wrapper">
            <a id="home-link" href="<c:url value="/"/>">
                <img class="logo tutv" src="<c:url value="/resources/img/Tutv.png"/>" alt="TUTV"> <span id="home-text">TUTV</span>
            </a> <!--#44d9e6-->
            <form id="global-search" method="get" class="navbar-form form-search" action="<c:url value="/searchResults"/>">
                <div class="form-group">
                    <img class="logo logo_icon" src="<c:url value="/resources/img/search.png"/>" alt="Search">
                </div>
                <div class="form-group">
                    <input type="text" id="global-search-input" name="name" class="show-search" placeholder="<spring:message code="search.search"/>">
                </div>
                <div class="form-group advanced-search" style="margin-top: 10px">
                    <a id="advancedSearchLink" href="<c:url value="/search"/>">
                        <spring:message code="search.advancedSearch"/>
                    </a>
                </div>
                <input type="submit" style="visibility: hidden;" />
            </form>
            <div class="all-left-navs">
                <section id="menu">
                    <ul class="menu list-unstyled">
                        <c:if test="${isLogged}">
                            <c:if test="${user.isAdmin}">
                                <li class="upcoming ">
                                    <a id="menu_users" href="<c:url value="/users"/>" title="<spring:message code="index.users"/>">
                                        <img class="logo logo_icon" src="<c:url value="/resources/img/users.png"/>" alt="<spring:message code="index.users"/>">
                                        <span><spring:message code="index.users"/></span>
                                    </a>
                                </li>
                            </c:if>
                            <li class="upcoming ">
                                <a id="menu_upcoming" href="<c:url value="/upcoming"/>" title="<spring:message code="index.upcoming"/>">
                                    <img class="logo logo_icon" src="<c:url value="/resources/img/upcoming.png"/>" alt="<spring:message code="index.upcoming"/>">
                                    <span><spring:message code="index.upcoming"/></span>
                                </a>
                            </li>
                            <li class="home ">
                                <a id="menu_watchlist" href="<c:url value="/watchlist"/>" title="<spring:message code="index.watchlist"/>">
                                    <img class="logo logo_icon" src="<c:url value="/resources/img/watchlist.png"/>" alt="<spring:message code="index.watchlist"/>">
                                    <span><spring:message code="index.watchlist"/></span>
                                </a>
                            </li>
                        </c:if>
                        <li class="explore">
                            <a id="menu_home" href="<c:url value="/"/>" title="<spring:message code="index.explore"/>">
                                <img class="logo logo_icon" src="<c:url value="/resources/img/explore.png"/>" alt="<spring:message code="index.explore"/>">
                                <span><spring:message code="index.explore"/></span>
                            </a>
                        </li>
                    </ul>
                </section>
                <c:if test="${isLogged}">
                    <section id="user-nav">
                        <h1>${user.userName}</h1>
                        <ul class="menu list-unstyled">
                            <li class="profile ">
                                <c:choose>
                                    <c:when test="${not empty userProfile && userProfile.id ne user.id}">
                                        <a href="<c:url value="/profile?id=${user.id}"/>" title="<spring:message code="index.profile"/>">
                                            <img class="logo logo_icon" src="<c:url value="/resources/img/profile.png"/>" alt="<spring:message code="index.profile"/>">
                                            <span><spring:message code="index.profile"/></span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a id="menu_profile"  href="<c:url value="/profile?id=${user.id}"/>" title="<spring:message code="index.profile"/>">
                                            <img class="logo logo_icon" src="<c:url value="/resources/img/profile.png"/>" alt="<spring:message code="index.profile"/>">
                                            <span><spring:message code="index.profile"/></span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </section>
                </c:if>
                <section>
                    <c:choose>
                        <c:when test="${isLogged}">
                            <a href="<c:url value="/logout"/>" class="signout-link" title="<spring:message code="index.signout"/>">
                                <img class="logo logo_icon" src="<c:url value="/resources/img/sign_out.png"/>" alt="<spring:message code="index.signout"/>">
                                <span><spring:message code="index.signout"/></span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/login"/>" class="signout-link" title="<spring:message code="index.signin"/>">
                                <img class="logo logo_icon" src="<c:url value="/resources/img/sign_in.png"/>" alt="<spring:message code="index.signin"/>">
                                <span><spring:message code="index.signin"/></span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </section>
            </div>
        </div>
    </div>
</div>
</html>
