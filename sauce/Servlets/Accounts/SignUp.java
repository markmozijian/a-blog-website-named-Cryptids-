package Servlets.Accounts;

import Tools.DBConnection;
import Tools.Images;
import Tools.PathFinder;
import Tools.SQLtools;
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
user can create an account
 */
public class SignUp extends HttpServlet {
    protected File uploadsFolder;
    protected String profilepicRelativePath;
    protected File tempFolder;
    protected String avatarRelativePath;


    DiskFileItemFactory factory = new DiskFileItemFactory();

    ServletFileUpload upload = new ServletFileUpload(factory);

    String username,password,realName,country,bio,avatar;
    Date birthday;
    String email = null;
    String picType = null;
    String filepath = null;
    boolean avatar_uploaded = false;
    File fullsizeImageFile = null;

    @Override
    public void init() throws ServletException {
        super.init();

        this.profilepicRelativePath = "assets/profilepics";
        avatarRelativePath = PathFinder.avatarPath;

        this.uploadsFolder = new File(getServletContext().getRealPath(profilepicRelativePath));
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);
        username = null; password = null; realName = null; country = null; bio = null;
        birthday = null; avatar = null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            avatar_uploaded = false;

            for (FileItem fi : fileItems) {

                if (fi.isFormField()){
                    String name = fi.getFieldName();
                    String value = new String (fi.get(), StandardCharsets.UTF_8);
                    switch (name){
                        case "username":
                            username = value;
                            break;
                        case "password":
                            password = fi.getString();
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

        User user = new User(username);
        user.setRealName(realName);
        user.setCountry(country);
        user.setBio(bio);
        user.setDateOfBirth(birthday);
        if (avatar_uploaded)
            user.setAvatar(avatar);
        user.setEmail(email);
        try (Connection connection = DBConnection.getDefault()){
            boolean usernameExists = UserDAO.usernameExists(connection,username);
            if (usernameExists){
                resp.sendRedirect("./signup");
                return;
            }
            UserDAO.addNewUser(connection,user,password);
            user = UserDAO.getUser(connection, username);//need ID number so get database to generate it
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            if (avatar_uploaded)
                addAvatar(req, resp, connection, filepath, picType);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        } catch (SQLException e){
            SQLtools.squeal(e,req,resp);
            return;
        }
        resp.sendRedirect("./home-page");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/frontend/signup.jsp").forward(req,resp);
    }

    protected void addAvatar(HttpServletRequest req, HttpServletResponse resp, Connection connection, String filepath, String type) throws ServletException, IOException,
            SQLException{
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        String thumbnail_path = getServletContext().getRealPath("/" + avatarRelativePath);
        String profilepic_path = getServletContext().getRealPath("/" + profilepicRelativePath + "/" + filepath);
        File thumbnail_folder = new File(thumbnail_path);
        File profilepic_file = new File(profilepic_path);
        String avatar = Images.storeThumbnail(profilepic_file,user.getID(),thumbnail_folder, type);
        user.setAvatar(avatar);
        UserDAO.updateUser(connection,user);
        profilepic_file.delete();//Once thumbnail is made and stored no need for original pic!
    }

    protected boolean imageUploaded(FileItem fi) throws Exception{
        picType = fi.getContentType().substring(6);
        String fileType = "." + picType;
        filepath = username+fileType;
        fullsizeImageFile = new File(uploadsFolder, filepath);
        fi.write(fullsizeImageFile);
        return true;
    }
}
