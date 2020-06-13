<%--
  Created by IntelliJ IDEA.
  User: jsul427
  Date: 7/06/2019
  Time: 2:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add account</title>
</head>
<body>
<form action="./add-account" enctype="application/x-www-form-urlencoded" method="post">
    UserID:<br>
    <input type="number" name="id"><br>
    Username:<br>
    <input type="text" name="username"><br>
    Password:<br>
    <input type="password" name="password"><br>
    <input type="submit" value="Add account">
</form>
</body>
</html>
