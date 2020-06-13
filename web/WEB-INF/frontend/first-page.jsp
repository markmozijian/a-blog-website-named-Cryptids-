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
    <title>First Page</title>
    <link rel="shortcut icon"
          href="${pageContext.request.contextPath}/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/firstpagestyle.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Asap:400,700' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/css/jquery.fadeshow-0.1.1.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
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

<div id="page-content">


    <section class="box-content box-1">



                <div class="background"></div>
                <div class="over-bg"></div>
                <div class="effect-container">
                    <noscript><h1>This side relies on Javascript</h1></noscript>
                    <section id="home" class="tab-pane fade in active">
                        <article class="home-content">
                            <header role="home-title">
                                <h2>We are
                                    <div id="typed-strings"><span>Jonathan</span> <span>Mark</span> <span>Hon</span> <span>Aiden</span>
                                    </div>
                                    <span id="typed" style="white-space:pre;"></span></h2>
                            </header>
                        </article>
                        <div id="countdown"></div>
                    </section>
                </div>

                <a href="./home-page" class="btn-start">Click to Enter</a>

    </section>
</div>




<!-- slider -->




<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/background.cycle.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/background.cycle-custom.js"></script>
<script src="${pageContext.request.contextPath}/js/html5shiv.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/typed.js" type="text/javascript"></script>
<script>
    $(function () {

        $("#typed").typed({
            // strings: ["Typed.js is a <strong>jQuery</strong> plugin.", "It <em>types</em> out sentences.", "And then deletes them.", "Try it out!"],
            stringsElement: $('#typed-strings'),
            typeSpeed: 100,
            backDelay: 1000,
            loop: true,
            contentType: 'html', // or text
            // defaults to false for infinite loop
            loopCount: false,
            callback: function () {
                foo();
            },
            resetCallback: function () {
                newTyped();
            }
        });

        $(".reset").click(function () {
            $("#typed").typed('reset');
        });

    });

    function newTyped() { /* A new typed object */
    }

    function foo() {
        console.log("Callback");
    }

</script>
</body>
</html>

