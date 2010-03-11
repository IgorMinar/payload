<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Payload Admin</title>

    <%@ page import="com.google.appengine.api.users.UserService, 
                     com.google.appengine.api.users.UserServiceFactory" %>
    <%! private UserService userService = UserServiceFactory.getUserService(); %>
    
</head>
<body>
    <div class="user.controls">
        <b><%= userService.getCurrentUser().getEmail() %></b> | 
        <a href="<%= userService.createLogoutURL("/helloworld") %>">Sign out</a>
    </div>
    <div class="admin.controls">
        <!-- TODO admin controls -->
        This is where the admin controls go.
    </div>
</body>
</html>