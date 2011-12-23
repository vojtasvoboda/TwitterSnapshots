<%-- 
    Document   : login
    Created on : 27.3.2011, 21:13:48
    Author     : Michal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <a href='<%=request.getAttribute("authUrl") %>'>Sign in with Twitter</a>
    </body>
</html>
