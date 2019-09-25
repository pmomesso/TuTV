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
                    <a href="/" title="Upcoming">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/upcoming.png"/>" alt="Upcoming">
                        <span><spring:message code="index.upcoming"/></span>
                    </a>
                </li>
                <li class="home ">
                    <a href="/" title="Watchlist">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/watchlist.png"/>" alt="Watchlist">
                        <span><spring:message code="index.watchlist"/></span>
                    </a>
                </li>
                <li class="profile ">
                    <a href="/" title="Profile">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/profile.png"/>" alt="Profile">
                        <span><spring:message code="index.profile"/></span>
                    </a>
                </li>
                <li class="explore active">
                    <a href="/" title="Explore">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/explore_active.png"/>" alt="Explore">
                        <span><spring:message code="index.explore"/></span>
                    </a>
                </li>
            </ul>
        </section>
        <section id="user-nav">
            <h1>agusosimani</h1>
            <ul class="menu list-unstyled">
                <li class="account ">
                    <a href="/" title="Settings">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/settings.png"/>" alt="Settings">
                        <span><spring:message code="index.settings"/></span>
                    </a>
                </li>
                <li class="help">
                    <a href="/" class="help-btn" title="Help">
                        <img class="logo logo_icon" src="<c:url value="/resources/img/help.png"/>" alt="Help">
                        <span><spring:message code="index.help"/></span>
                    </a>
                </li>
            </ul>
        </section>
        <section>
            <a href="/" class="signout-link" title="Sign out">
                <img class="logo logo_icon" src="<c:url value="/resources/img/sign_out.png"/>" alt="Sign out">
                <span><spring:message code="index.signout"/></span>
            </a>
        </section>
    </div>
</div>
</html>
