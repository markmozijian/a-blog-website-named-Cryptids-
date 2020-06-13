<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jsul427
  Date: 6/06/2019
  Time: 1:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset password</title>
</head>
<body>
<c:if test="${user.ID < 0}">
    <h1>Enter a username to send a reset password email to the user</h1>
</c:if>
<c:if test="${resetted == 1}">
    <div class="alert-success">
        If the account account as an email associated with it, an email with a token should have been sent to the corresponding email address.
    </div>
</c:if>
<form method="post" action="./reset-password">
Username:<br>
    <input type="text" name="username" value="${username}">
    <input type="submit" value="Reset">
</form>

</body>
</html>
