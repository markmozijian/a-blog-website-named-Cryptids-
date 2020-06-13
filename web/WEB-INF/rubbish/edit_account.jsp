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
    <title>Edit account</title>
</head>
<body>
<h4>Edit account info</h4>
<form method="post" action="./edit-account" enctype="multipart/form-data">
    Username:<br>
    <input name="username" id="input_username" type="text" value="${user.username}"><br>
    Email:<br>
    <input type="email" id="input_email" name="email" value="${user.email}"><br>
    <label for="input_avatar">Avatar:</label>
    <input name="avatar" id="input_avatar" type="file" accept="image/*"><br>
    Real Name:<br>
    <input name="realName" id="input_rlname" type="text" value="${user.realName}"><br>
    Date of Birth:<br>
    <input name="birthday" id="input_bday" type="date" value="${user.dateOfBirth}"><br>
    Country:<br>
    <input name="country" id="input_country" type="text" value="${user.country}"><br>
    Bio:<br>
    <textarea cols="20" rows="20" id="input_bio" name="bio">${user.bio}</textarea>
    <br>
    <button type="submit">Update</button>
</form>

<hr>
<h4>Change password</h4>
<form method="post" action="./edit-password" enctype="application/x-www-form-urlencoded">
    Current password:<br>
    <input type="password" name="old_password"><br>
    New password:<br>
    <input type="password" name="new_password">
    <br>
    <button type="submit">Update</button>
</form>

</body>
</html>
