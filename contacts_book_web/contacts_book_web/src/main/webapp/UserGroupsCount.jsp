<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="jspBean"
      class="com.bubna.view.JSPBean"></jsp:useBean>

<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <jsp:setProperty  name="jspBean" property="listen" value="userGroupsCount"/>
        <%
            ArrayList al = (ArrayList) jspBean.getAnswer();
        %>

        <p>user groups count:<br>
            <%
                for(int i = 0; i < al.size(); i++) {
                    String data = (String) al.get(i);
            %>
                <%= data %><br>
            <%  } %>
        </p>

    </body>
</html>
