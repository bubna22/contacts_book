<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <p>user contacts count:<br>
            <c:forEach items="${userContactsCount}" var="ucc">
                <h2>${ucc}</h2>
            </c:forEach>
        </p>

    </body>
</html>
