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
<h1>Are you sure you want to purge ${article.title}?</h1>
<form action="" method="post">
    <input type="hidden" value="${article.ID}">
    <button type="submit">Yes</button>
</form>
<form action="./view_article?articleID=${article.ID}" method="get">
    <input type="hidden" value="${article.ID}">
    <button type="submit">No</button>
</form>
</body>
</html>
