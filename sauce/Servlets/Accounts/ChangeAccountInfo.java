package Servlets.Accounts;

import Tools.DBConnection;
import Tools.SessionTools;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import users.User;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
/*
User can edit their profile
 */
public class ChangeAccountInfo extends SignUp {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            avatar_uploaded = false;

            for (FileItem fi : fileItems) {
                if (fi == null){
                    continue;
                }

                if (fi.isFormField()){
                    String name = fi.getFieldName();
                    String value = new String (fi.get(), StandardCharsets.UTF_8);
                    switch (name){
                        case "username":
                            username = value;
                            break;
                        case "realName":
                            realName = value;
                            break;
                        case "country":
                            country = value;
                            break;
                        case "bio":
                            bio = value;
                            break;
                        case "default_avatar":
                            avatar = value;
                            break;
                        case "email":
                            email = value;
                            break;
                        case "birthday":
                            try {
                                birthday = Date.valueOf(value);
                            } catch (IllegalArgumentException e){
                                birthday = null;
                            }
                            break;
                    }
                } else if ((fi.getContentType().startsWith("image/"))) {
                    avatar_uploaded = imageUploaded(fi);
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        User user = SessionTools.getSessionUser(req);
        user.setUsername(username);
        user.setRealName(realName);
        user.setCountry(country);
        user.setBio(bio);
        user.setDateOfBirth(birthday);
        user.setEmail(email);
        if (avatar_uploaded)
            user.setAvatar(avatar);
        try (Connection connection = DBConnection.getDefault()){
            UserDAO.updateUser(connection,user);
            HttpSession session = req.getSession();
            user = UserDAO.getUser(connection,user.getID());
            session.setAttribute("user",user);
            if (avatar_uploaded) {
                addAvatar(req, resp, connection, filepath, picType);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        resp.sendRedirect("./home-page");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/accounts/edit_account.jsp").forward(req,resp);
    }
}
