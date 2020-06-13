<%--
  Created by IntelliJ IDEA.
  User: jsul427
  Date: 6/06/2019
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Destroy ${victim.username}?</title>
</head>
<body>
<h1>Are you sure you want to destroy ${victim.username}?</h1>
<form action="./murder" method="post">
    <input type="hidden" name="victimID" value="${victim.ID}">
    Enter password to confirm:<br>
    <input type="password" name="password"><br>
    <button type="submit">Yes</button>
</form>
<form action="./home-page" method="get">
    <button type="submit">No</button>
</form>
</body>
</html>
