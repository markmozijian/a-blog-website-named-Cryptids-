<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-02
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${article != null}">
        <title>Editing ${article.title}</title>
    </c:if>
    <c:if test="${article == null}">
        <title>New Article</title>
    </c:if>
    <!-- jQuery -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- jQuery end -->
    <script src="${pageContext.request.contextPath}/js/post_form.js"></script>
    <!-- Include stylesheet -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

    <!-- Include the Quill library -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
</head>
<body>


<c:if test="${user == null}">
    Must be logged in to post or edit an article
</c:if>

<c:if test="${user.ID != null}">
    <c:if test="${article == null}">
        <form method="post" action="./new_article" onsubmit="post_ajax(this)" enctype="multipart/form-data">
    </c:if>
    <c:if test="${article != null}">
        <form method="post" action="./edit_article" onsubmit="post_ajax(this)" enctype="multipart/form-data">
            <input type="hidden" name="articleID" value="${article.ID}">
    </c:if>

        Title:<br>
        <input name="title" value="${article.title}"><br>
        Main Picture:<br>
            <input type="file" accept="image/*" name="mainPic"><br>
            Video or Audio:<br>
            <input type="file" accept="audio/*,video/*" name="media"><br>
            Content:<br>
            <input type="hidden" id="hidden-form" value="" name="content">
            <!-- Actual editor into body -->

            <div id="standalone-container">
                <div id="toolbar-container">
                                    <span class="ql-formats">
                                      <select class="ql-font"></select>
                                      <select class="ql-size"></select>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-bold"></button>
                                      <button class="ql-italic"></button>
                                      <button class="ql-underline"></button>
                                      <button class="ql-strike"></button>
                                    </span>
                    <span class="ql-formats">
                                      <select class="ql-color"></select>
                                      <select class="ql-background"></select>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-script" value="sub"></button>
                                      <button class="ql-script" value="super"></button>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-header" value="1"></button>
                                      <button class="ql-header" value="2"></button>
                                      <button class="ql-blockquote"></button>
                                      <button class="ql-code-block"></button>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-list" value="ordered"></button>
                                      <button class="ql-list" value="bullet"></button>
                                      <button class="ql-indent" value="-1"></button>
                                      <button class="ql-indent" value="+1"></button>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-direction" value="rtl"></button>
                                      <select class="ql-align"></select>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-link"></button>
                                      <button class="ql-image"></button>
                                      <button class="ql-video"></button>
                                      <button class="ql-formula"></button>
                                    </span>
                    <span class="ql-formats">
                                      <button class="ql-clean"></button>
                                    </span>
                </div>
                <div id="editor-container"></div>
            </div>
            <!-- Initialize Quill editor -->
            <script>
                window.addEventListener("load",fill_quill);
                var quill = new Quill('#editor-container', {
                    modules: {
                        formula: true,
                        syntax: true,
                        toolbar: '#toolbar-container'
                    },
                    placeholder: 'An article is waiting to be written...',
                    theme: 'snow'
                });
                function fill_quill() {
                    let editor = document.getElementsByClassName("ql-editor");
                    if (editor.length <= 0){
                        return false;
                    }
                    editor[0].innerHTML = String.raw `${article.content}`;
                    return true;
                }
            </script>
        <br>
        <button type="submit">Submit</button>
    </form>


</c:if>



</body>
</html>
