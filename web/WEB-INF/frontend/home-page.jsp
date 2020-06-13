<%--
  Created by IntelliJ IDEA.
  User: Markmo
  Date: 2019/6/4
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/favicon.ico" />
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-page.css">
    <link href='http://fonts.googleapis.com/css?family=Asap:400,700' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/css/jquery.fadeshow-0.1.1.min.css" rel="stylesheet">
    <!-- boot strap script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!-- Evil scripts by Jonathan!!! -->
    <script src="${pageContext.request.contextPath}/js/cryptid_utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/my_articles.js"></script>
    <style>
        .loader {
            border: 16px solid #f3f3f3; /* Light grey */
            border-top: 16px solid #0aac75; /* Blue */
            border-radius: 50%;
            width: 120px;
            height: 120px;
            animation: spin 2s linear infinite;
            left: 0;
            right: 0;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 8%;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
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
                    <li>
                        <a href="./new_article">New Article</a>
                    </li>
                    <li>
                        <a class="page-scroll" id="my-articles-button" href="#" onclick="return article_button_toggle(${user.ID})">My Articles</a>
                    </li>
                    <li>
                        <a class="page-scroll" id="all-articles-button" href="#" onclick="return article_button_toggle(${user.ID})">All Articles</a>
                    </li>
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
</nav><!-- Navigation -->

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
<div id="page-content">


    <section class="box-content box-1">
        <div class="container">

            <div class="row">

                <noscript><h1>This side relies on Javascript</h1></noscript>

                <div class="loader"></div>

                <div class="col-md-4 article-col">
                <%--<c:forEach items="${articles1}" var="article">
                        <div class="box-item">
                            <img src="images/main-bg.jpg" class="img-responsive"/>
                            <div class="content">
                                <h3>${article.title}</h3>
                                <p>${article.content}</p>
                                <a href="./view_article?articleID=${article.ID}">Read More...</a>
                                <br><br>
                                <span>${article.displayTime} BY ${article.author.username}</span><br>
                            </div>
                        </div>
                    </c:forEach>--%>
                </div>


                <div class="col-md-4 article-col">

                <%--<c:forEach items="${articles2}" var="article">
                        <div class="box-item">
                            <img src="images/main-bg.jpg" class="img-responsive"/>
                            <div class="content">
                                <h3>${article.title}</h3>
                                <p>${article.content}</p>
                                <a href="./view_article?articleID=${article.ID}">Read More...</a>
                                <br><br>
                                <span>${article.displayTime} BY ${article.author.username}</span><br>
                            </div>
                        </div>
                    </c:forEach>--%>

                </div>
                <div class="col-md-4 article-col">
                <%--<c:forEach items="${articles3}" var="article">
                        <div class="box-item">
                            <img src="images/main-bg.jpg" class="img-responsive"/>
                            <div class="content">
                                <h3>${article.title}</h3>
                                <p>${article.content}</p>
                                <a href="./view_article?articleID=${article.ID}">Read More...</a>
                                <br><br>
                                <span>${article.displayTime} BY ${article.author.username}</span><br>
                            </div>
                        </div>
                    </c:forEach>--%>

                </div>
            </div>
        </div>
    </section>

</div>

<!-- FOOTER -->
<footer>
    <div class="wrap-footer">
        <div class="container">
            <div class="row">
                <div class="col-footer col-md-3">
                    <c:if test="${user != null && user.ID < 0}">
                        <div>
                            <h2 class="footer-title">Admin options</h2>
                            <br>
                            <a href="./add-account" class="btncustom">Add account</a>
                            <br>
                            <h4 class="footer-title">Send reset email to user</h4>
                            <form method="post" action="./reset-password">
                                Username:<br>
                                <input type="text" name="username" class="inputString" value="">
                                <input type="submit"  value="Reset" class="btncustom">
                            </form>
                        </div>
                    </c:if>
                </div>
                <div class="col-footer col-md-3">
                    <h2 class="footer-title">About Us</h2>
                    <div class="textwidget">Some site about documenting cryptids <br> <br>
                        A cryptid is an animal of some sort with a disputed or unconfirmed existence
                    </div>
                </div>
                <div class="col-footer col-md-3">
                    <h2 class="footer-title">Search</h2>
                    <form action="javascript:void(0);" onsubmit="search_articles()">
                        <input type="text" id="search-bar" value="">
                        <input type="submit" value="Search" class="btncustom">
                    </form>
                    <c:if test="${user != null}">
                        <button class="btncustom" onclick="liked_articles()">Liked Articles</button>
                    </c:if>
                </div>
                <div class="col-footer col-md-3">
                    <c:if test="${user != null && user.ID < 0}">
                        <h1 class="footer-title">Send email to user</h1>
                        <c:if test="${1 == sent}">
                            <div class="alert-success">Email sent</div>
                        </c:if>
                        <c:if test="${1 == failed}">
                            <div class="alert-info">Email send failed</div>
                        </c:if>
                        <form action="./send-email" method="post">
                            Username:<br>
                            <input type="text" name="username" class="inputString" value="">
                            Subject:<br>
                            <input type="text" name="subject" class="inputString" value="">
                            Content:<br>
                            <textarea class="text-area" style="width: 100%" name="content"></textarea>
                            <br>
                            <br>
                            <input type="submit"  value="Send" class="btncustom" >
                        </form>
                    </c:if>
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

<!-- Log Out -->
<script src="${pageContext.request.contextPath}/js/box.js"></script>


</body>
</html>

