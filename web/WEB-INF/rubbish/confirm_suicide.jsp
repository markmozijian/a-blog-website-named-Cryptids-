<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-04
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Are you sure you want to purge?</title>
</head>
<body>
<h1>Are you sure you want to destroy your account ${user.username}?</h1>
<form action="./suicide" method="post">
    Enter password to confirm:<br>
    <input type="password" name="password"><br>
    <button type="submit">Yes</button>
</form>
<form action="./home-page" method="get">
    <button type="submit">No</button>
</form>
</body>
</html>
