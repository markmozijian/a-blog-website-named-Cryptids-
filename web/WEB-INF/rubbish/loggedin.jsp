<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-02
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logged in</title>
</head>
<body>
<c:if test="${user != null}">
    <h1>Logged in as ${user.name}</h1>
    <img src="assets/avatars/${user.avatar}">
    <h3>Stuff to do</h3>
    <ul>
        <li><a href="./new_article">Create new article</a></li>
        <li><a href="./all_articles">View all articles</a></li>
    </ul>
</c:if>
<c:if test="${user == null}">
    You must login to view this page
</c:if>
</body>
</html>
