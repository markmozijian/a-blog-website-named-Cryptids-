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
    <title>Sign up</title>
</head>
<body>

<form method="post" action="./signup" enctype="multipart/form-data">
    Username:<br>
    <input name="username" id="input_username" type="text" required><br>
    Password:<br>
    <input name="password" id="input_name" type="password" required><br>
    <label for="input_avatar">Avatar:</label>
    <input name="avatar" id="input_avatar" type="file" accept="image/*"><br>
    Real Name:<br>
    <input name="realName" id="input_rlname" type="text"><br>
    Date of Birth:<br>
    <input name="birthday" id="input_bday" type="date"><br>
    Country:<br>
    <input name="country" id="input_country" type="text"><br>
    Bio:<br>
    <textarea cols="20" rows="20" id="input_bio" name="bio"></textarea>
    <br>
    <button type="submit">Sign up</button>
</form>

</body>
</html>
