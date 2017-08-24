<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>contacts book</title>
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
    <script src="js/functions.js"></script>
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>Title</title>
</head>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/main">contacts book</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/contacts">contacts</a></li>
                <li><a href="${pageContext.request.contextPath}/groups">groups</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Statistics <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/inactive_users">Inactive users</a></li>
                        <li><a href="${pageContext.request.contextPath}/user_avg_groups_count">Users avg groups count</a></li>
                        <li><a href="${pageContext.request.contextPath}/user_avg_contacts_count">Users avg contacts count</a></li>
                        <li><a href="${pageContext.request.contextPath}/user_groups_count">Users groups count</a></li>
                        <li><a href="${pageContext.request.contextPath}/user_contacts_count">Users contacts count</a></li>
                        <li><a href="${pageContext.request.contextPath}/users_count">Users count</a></li>
                    </ul>
                </li>
            </ul>
            <%
                     Cookie cookie = null;
                     Cookie[] cookies = null;

                     // Get an array of Cookies associated with the this domain
                     cookies = request.getCookies();
                     boolean loggedIn = false;
                     if( cookies != null ) {

                        for (int i = 0; i < cookies.length; i++) {
                            cookie = cookies[i];
                            if (cookie.getName().equals("user")) {
                                loggedIn = true;
                                out.print(
                                    "<ul class=\"nav navbar-nav navbar-right\"><li><a href=\"#\"> Welcome " +
                                      cookie.getValue() +
                                    "</a></li></ul>"
                                );
                                break;
                            }
                        }
                     }
                     if (!loggedIn) {
                        out.print(
                            "<form class=\"navbar-form navbar-left\" action=\"login\" method=\"POST\">" +
                            "<div class=\"form-group\">" +
                            "<input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"login\">" +
                            "<input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"password\">" +
                            "</div>" +
                            "<button type=\"submit\" class=\"btn btn-default\">Submit</button>" +
                            "</form>"
                        );
                     }
            %>

        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>