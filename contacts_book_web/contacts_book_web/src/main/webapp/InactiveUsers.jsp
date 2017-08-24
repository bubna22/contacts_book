<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <p>inactive users:<br>
            <c:forEach items="${inactiveUsers}" var="inactiveUser">
                <h2>${inactiveUser}</h2>
            </c:forEach>
        </p>

    </body>
</html>
