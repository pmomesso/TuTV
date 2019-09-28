<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<div class="wrapper">
    <a id="home-link" href="<c:url value="/"/>">
        <img class="logo tutv" src="<c:url value="/resources/img/Tutv.png"/>" alt="TUTV"> <span id="home-text">TUTV</span>
    </a> <!--#44d9e6-->
    <%@ include file="searchForm.jsp" %>
    <div class="all-left-navs">
        <section id="menu">
            <ul class="menu list-unstyled">
                <li class="upcoming ">
                    <a href="/upcoming" title="Upcoming">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/upcoming.png"/>" alt="Upcoming">
                        <span><spring:message code="index.upcoming"/></span>
                    </a>
                </li>
                <li class="home ">
                    <a href="/watchlist" title="Watchlist">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/watchlist.png"/>" alt="Watchlist">
                        <span><spring:message code="index.watchlist"/></span>
                    </a>
                </li>
                <li class="explore">
                    <a href="/" title="Explore">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/explore.png"/>" alt="Explore">
                        <span><spring:message code="index.explore"/></span>
                    </a>
                </li>
            </ul>
        </section>
        <section id="user-nav">
            <h1>agusosimani</h1>
            <ul class="menu list-unstyled">
                <li class="profile ">
                    <a href="/profile" title="Profile">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/profile.png"/>" alt="Profile">
                        <span><spring:message code="index.profile"/></span>
                    </a>
                </li>
            </ul>
        </section>
        <section>
            <a href="/logout" class="signout-link" title="<spring:message code="index.signout"/>">
                <img class="logo logo_icon" src="<c:url value="/resources/img/sign_out.png"/>" alt="<spring:message code="index.signout"/>">
                <span><spring:message code="index.signout"/></span>
            </a>
        </section>
    </div>
</div>
</html>
