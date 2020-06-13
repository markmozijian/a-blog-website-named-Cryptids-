<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-02
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<p>
    <a href="./signup">Sign up</a>
</p>

<form method="post" action="./login" enctype="application/x-www-form-urlencoded">
    Username:<br>
    <input type="text" name="username" required><br>
    Password:<br>
    <input type="password" name="password" required><br>
    <button type="submit">Login in</button>
</form>

<c:if test="${failed_login == 1}}">
    Username or password is incorrect
</c:if>

</body>
</html>
