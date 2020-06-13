<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 2019-06-03
  Time: 00:11
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
    <title>Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Asap:400,700' rel='stylesheet' type='text/css'>
    <link href="${pageContext.request.contextPath}/css/jquery.fadeshow-0.1.1.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signup.css" rel="stylesheet" type="text/css">

    <script src="${pageContext.request.contextPath}/quill-image-resize-module-master/image-resize.min.js"></script>

    <script>
        function select_avatar(avatar){
            var default_avatar = document.getElementById("input_default_avatar");
            var img_name = "default-" + avatar + ".jpg";
            default_avatar.value = img_name;
        }
        function show_selected(selected){
            var avatar_selector = document.getElementById("avatar-selector");
            var lists = avatar_selector.children;
            for (let i = 0; i < lists.length; i++) {
                if (selected == i){
                    lists[i].firstElementChild.setAttribute("style","border: 3px solid black;");
                } else {
                    lists[i].firstElementChild.setAttribute("style","border: none;");
                }
            }
        }
        window.onload = function () {
            var avatar_selector = document.getElementById("avatar-selector");
            var lists = avatar_selector.children;
            for (let i = 0; i < lists.length; i++) {
                lists[i].firstElementChild.addEventListener("click",function () {
                   select_avatar(i+1);
                   show_selected(i);
                });
            }
        }
    </script>

    <script src="${pageContext.request.contextPath}/js/cryptid_utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/checkUsername.js"></script>
    <script src="${pageContext.request.contextPath}/js/post_form.js"></script>
    <!-- Include stylesheet -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">



    <!-- Include the Quill library -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
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
                <li>
                    <a class="page-scroll" href="./login">Login</a>
                </li>
                <li>
                    <a class="page-scroll" href="./signup">Sign Up</a>
                </li>
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
        <h1 class="site-title">
            <a href="./home-page">
                <span>CRYPTIDS</span>
            </a>
        </h1>
        <h2 class="site-description">Welcome to Us !</h2>
    </div>

</header>
<div id="page-content">
    <div class="sectionWrapper">
        <div class="sign-up-form">
            <div id="sign-up-password">
                <h1>Sign Up</h1>
                <span class="login-account">
                    <a href=" ">Or Login in</a>
            </span>
                <div class="login-password-container">
                    <form method="post" id="sign-up-form" action="./signup" enctype="multipart/form-data" onsubmit="return post_ajax_required(this)">
                        <div id="aa">
                            Username:<span id="username-taken" class="alert-warning invisible">Username already taken</span><br>
                            <input class="inputString" name="username" id="input_username" type="text" required><br>
                            Password:<br>
                            <input class="inputString" name="password" id="input_name" type="password" required><br>
                            <label for="input_avatar">Avatar:</label>
                            <input type="hidden" id="input_default_avatar" name="default_avatar">
                            <section class="newpic">
                                Default avatar:
                                <ul id="avatar-selector">
                                    <li><img src="images/01.jpg"></li>
                                    <li><img src="images/02.jpg"></li>
                                    <li><img src="images/03.jpg"></li>
                                    <li><img src="images/04.jpg"></li>
                                    <li><img src="images/05.jpg"></li>
                                </ul>
                            </section>
                            Upload your avatar:
                            <input class="inputString" name="avatar" id="input_avatar" type="file" accept="image/*"><br>
                            Email <span class="label-info">not required but useful for password recovery</span>:<br>
                            <input class="inputString" name="email" id="input_email" type="email"><br>
                            Real Name:<br>
                            <input class="inputString" name="realName" id="input_rlname" type="text" required><br>
                            Date of Birth:<br>
                            <input class="inputString" name="birthday" id="input_bday" type="date" required><br>
                            Country:<br>
                            <input class="inputString" name="country" id="input_country" type="text" required><br>
                            Bio:<br><span id="alert-required" hidden class="alert-warning">You're supposed to enter a bio. Just enter whatever, you can edit it later on.</span>
                            <input name="bio" id="hidden-form" type="hidden">
                            <br>
                            <!-- Create the editor container -->
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
                                var quill = new Quill('#editor-container', {
                                    modules: {
                                        formula: true,
                                        syntax: true,
                                        toolbar: '#toolbar-container',
                                        imageResize: {}
                                    },
                                    placeholder: 'Compose an epic...',
                                    theme: 'snow'
                                });
                            </script>
                            <br>
                        </div>
                        <button class="signup-btn" type="submit">Sign up</button>
                    </form>
                </div>
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
