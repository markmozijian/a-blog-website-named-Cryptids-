<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Markmo
  Date: 2019/6/5
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/favicon.ico" />
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
    <script src="${pageContext.request.contextPath}/js/edit_comment.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment.css">
    <link href='http://fonts.googleapis.com/css?family=Asap:400,700' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/css/jquery.fadeshow-0.1.1.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/evil.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/quill-image-resize-module-master/image-resize.min.js"></script>
</head>
<body class="sub-page">
<!-- /////////////////////////////////////////Navigation -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand page-scroll" href="./first-page">CRYPTIDS</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a class="page-scroll" href="./first-page">Home</a>
                </li>
                <li>
                    <a class="page-scroll" href="./home-page">Blog</a>
                </li>
                <c:if test="${user == null}">
                    <li>
                        <a class="page-scroll" href="./login">Login</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="./signup">Sign Up</a>
                    </li>
                </c:if>

                <c:if test="${user != null}">

                    <li class="dropdown">
                        <a class="page-scroll" href="#" onclick="false">Profile</a>
                        <ul class="dropdown-content">
                            <li>
                                <a href="./profile">View Profile</a>
                            </li>
                            <li>
                                <a href="./edit-account">Edit Profile</a>
                            </li>
                            <li>
                                <a href="./edit-password">Edit Password</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a class="page-scroll" href="./logout">Log Out</a>
                    </li>
                </c:if>
                <li>
                    <a class="page-scroll" href="./gallery">Gallery</a>
                </li>

            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<!-- Navigation -->

<!-- Background Gradients-->
<div class="site-gradients">
    <div class="site-gradients-media">
        <figure>
            <img src="Another%20Yosemite%20short%20movie%20project%20%E2%80%93%20Modern_files/PcLGXNjMTdiFVKTrElCl__DSC2245.jpg"
                 alt="PcLGXNjMTdiFVKTrElCl__DSC2245"
                 srcset="https://themedemos.webmandesign.eu/modern/wp-content/uploads/sites/8/2014/11/PcLGXNjMTdiFVKTrElCl__DSC2245.jpg 1920w, https://themedemos.webmandesign.eu/modern/wp-content/uploads/sites/8/2014/11/PcLGXNjMTdiFVKTrElCl__DSC2245-420x280.jpg 420w, https://themedemos.webmandesign.eu/modern/wp-content/uploads/sites/8/2014/11/PcLGXNjMTdiFVKTrElCl__DSC2245-744x497.jpg 744w, https://themedemos.webmandesign.eu/modern/wp-content/uploads/sites/8/2014/11/PcLGXNjMTdiFVKTrElCl__DSC2245-1200x801.jpg 1200w"
                 sizes="(max-width: 1617px) 100vw, 1617px" height="1080" width="1617">
        </figure>
    </div>
</div>


<header class="container">
    <div class="site-branding">
        <c:if test="${user == null}">
            <h1 class="site-title">

                <a href="./login">
                    <span>VISITOR</span>
                </a>
            </h1>
            <h2 class="site-description">Please login !</h2>
        </c:if>
        <c:if test="${user != null}">
            <h1 class="site-title">

                <a href="./profile">
                    <span>${user.username}</span>
                </a>
            </h1>
            <h2 class="site-description">Welcome to Us !</h2>
        </c:if>
    </div>

</header>

<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page">
    <div class="container">
        <div class="row">
            <div id="main-content" class="edit-content">


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
                                    toolbar: '#toolbar-container',
                                    imageResize: {}
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
                        <button type="submit" class="btncustom">Submit</button>
                    </form>


                    </c:if>


            </div>


        </div>


    </div>
</div>

    <!-- FOOTER -->
    <footer>
        <div class="wrap-footer">
            <div class="container">
                <div class="row">
                    <div class="col-footer col-md-3">
                    </div>
                    <div class="col-footer col-md-3">
                        <h2 class="footer-title">About Us</h2>
                        <div class="textwidget">Some site about documenting cryptids <br> <br>
                            A cryptid is an animal of some sort with a disputed or unconfirmed existence
                        </div>
                    </div>
                    <div class="col-footer col-md-3">

                    </div>
                    <div class="col-footer col-md-3">
                    </div>
                </div>
            </div>
        </div>
        <div class="bottom-footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4">
                        <ul class="list-inline quicklinks">
                            <li><a href="./privacy-policy">Privacy Policy</a>
                        </li>
                        <li><a href="./terms-of-use">Terms of Use</a>
                        </li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- jQuery -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/js/agency.js"></script>

    <!-- Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/js/jquery.easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/classie.js"></script>
    <script src="${pageContext.request.contextPath}/js/cbpAnimatedHeader.js"></script>

</body>
</html>
