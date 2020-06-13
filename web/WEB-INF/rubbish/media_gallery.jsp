<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jsul427
  Date: 11/06/2019
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Media Gallery</title>
    <link href="${pageContext.request.contextPath}/css/gallery.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cryptid_utils.js"></script>
    <script src = "${pageContext.request.contextPath}/js/loadMediaGallery.js"></script>
</head>
<body>
<c:if test="${user != null}">
    <button type="button" onclick="load_author_media(${user.ID})">My media</button>
    <button type="button" onclick="load_all_media()">All media</button>
</c:if>
<hr>
<div id="mainPics"></div>
<hr>
<div id="images">
</div>
<hr>
<div id="media"></div>
<hr>
<div id="videos"></div>
</body>
</html>
