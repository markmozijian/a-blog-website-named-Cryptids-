<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-02
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${article.title}</title>
    <script src="../js/edit_comment.js"></script>
</head>
<body>
<h1>${article.title}</h1>
<blockquote>${article.content}</blockquote>

<c:if test="${article.author.ID == user.ID}">
    <a href="./edit_article?articleID=${article.ID}">Edit This!</a>
    <a href="./delete_article?articleID=${article.ID}">Delete This!</a>
    <a href="./purge_article?articleID=${article.ID}">Purge This!</a>
</c:if>
<hr>
<h3>Comments</h3>
<hr>
<c:forEach items="${comments}" var="comment">
    <div class="comment">
    <h5>${comment.authorName}</h5>
    <h6>${comment.lastUpdate}</h6>
    <blockquote id="comment-${comment.ID}">${comment.content}</blockquote>
    <c:if test="${comment.author.ID == user.ID || article.author.ID == user.ID}">
        <input type="button" onclick="location.href='./delete_comment?commentID=${comment.ID}';" value="Delete" />
    </c:if>
    <c:if test="${comment.author.ID == user.ID}">
        <input type="button" onclick="location.href='./purge_comment?commentID=${comment.ID}';" value="Purge" />
        <input type="button" id="edit-comment-${comment.ID}" onclick="edit_comment(${comment.ID});" value="Edit" />
    </c:if>
        <form action="./edit_comment" id="comment-editor-${comment.ID}" method="post" hidden>
            <input type="hidden" value="${comment.ID}" name="commentID">
            <textarea name="content" id="comment-editor-field-${comment.ID}">${comment.content}</textarea>
            <input type="submit" value="Submit" id="comment-editor-button-${comment.ID}">
        </form>
    </div>
    <hr>
</c:forEach>
<h3>New Comment</h3>
<hr>
<form method="post" enctype="application/x-www-form-urlencoded" action="./comment_article">
    <input type="hidden" name="articleID" value="${article.ID}">
    <textarea rows="4" cols="50" name="comment"></textarea>
    <br>
    <button type="submit">Comment</button>
</form>
</body>
</html>
