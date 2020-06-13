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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/favicon.ico" />
    <title>Terms of Use</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Asap:400,700' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/css/signup.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/gallery.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cryptid_utils.js"></script>
    <script src = "${pageContext.request.contextPath}/js/loadMediaGallery.js"></script>
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

<div id="page-content" class="single-page">
    <div class="container">
        <div class="row">
            <div id="main-content" class="edit-content">


                <h1>Terms of Use</h1>
                <p>
                    We reserve the right to steal your content.
                </p>

            </div>


        </div>


    </div>
</div>




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