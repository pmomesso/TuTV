<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TUTV Error</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/error.css"/>">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template">
                    <h1>Oops!</h1>
                    <h2><spring:message key="${status}"/></h2>
                    <div class="error-details">
                        <spring:message key="${body}"/>
                    </div>
                    <div class="error-actions">
                        <a href="<c:url value="/"/>" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                            <spring:message key="error.goHome"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
