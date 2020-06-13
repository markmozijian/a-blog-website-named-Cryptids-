<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-02
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>List of all articles</title>
</head>
<body>
<ul>
    <c:forEach items="${articles}" var="article">
        <li><a href="./view_article?articleID=${article.ID}">${article.title}</a></li>
    </c:forEach>
</ul>
</body>
</html>
