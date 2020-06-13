package Servlets.Accounts;

import Tools.DBConnection;
import Tools.ParseTools;
import Tools.SessionTools;
import users.User;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
Profiles can be viewed. if logged in your profile is shown by default
 */
public class Profile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userStr = req.getParameter("user");
        if (ParseTools.isInt(userStr)){
            int userID = Integer.parseInt(userStr);
            try(Connection connection = DBConnection.getDefault()){
                User user = UserDAO.getUser(connection,userID);
                req.setAttribute("view_user",user);
            } catch (SQLException e){
                e.printStackTrace();
                req.setAttribute("view_user",
                        SessionTools.getSessionUser(req));
            }
        } else {
            req.setAttribute("view_user",
                    SessionTools.getSessionUser(req));
        }
        req.getRequestDispatcher("./WEB-INF/frontend/profile.jsp").forward(req,resp);
    }
}
