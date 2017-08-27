<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <p>user groups count:<br>
            <c:forEach items="${userGroupsCount}" var="ugc">
                <h2>${ugc}</h2>
            </c:forEach>
        </p>

    </body>
</html>
