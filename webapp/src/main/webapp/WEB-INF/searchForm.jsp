<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
    <form id="global-search" class="navbar-form form-search" action="<c:url value="/search"/>">
    <div class="form-group">
        <img class="logo logo_icon" src="<c:url value="/resources/img/search.png"/>" alt="Search">
    </div>
    <div class="form-group">
        <input type="text" id="global-search-input" name="search" class="show-search" placeholder="<spring:message code="search.search"/>">
    </div>
    <div class="form-group pull-left">
        <div class="form-check" style="margin-top: 8px">
            <input class="form-check-input" type="radio" name="op" id="searchByName" value="name" checked>
            <label class="form-check-label" for="searchByName" style="font-weight: lighter">
                <font color="#808080"><spring:message code="search.name"/></font>
            </label>
        </div>
        <div class="form-check pull-left" style="margin-top: 4px">
            <input class="form-check-input" type="radio" name="op" id="searchByGenre" value="genre">
            <label class="form-check-label" for="searchByGenre" style="font-weight: lighter">
                <font color="808080"><spring:message code="search.genre"/></font>
            </label>
        </div>
    </div>
    <input type="submit" style="visibility: hidden;" />
    </form>
</html>