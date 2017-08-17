<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="jspBean"
      class="com.bubna.view.JSPBean"></jsp:useBean>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <p>user AVG groups count:
            <jsp:setProperty  name="jspBean" property="listen" value="userGroupsAVGCount"/>
            <jsp:getProperty name="jspBean" property="answer" />
        </p>

    </body>
</html>
