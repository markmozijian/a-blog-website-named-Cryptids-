package Servlets.Accounts;

import Tools.DBConnection;
import users.User;
import users.UserDAO;
import users.UserWithPW;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
/*
user can change their password
 */
public class ChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = DBConnection.getDefault()){
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute("resetPW");
            boolean resetPW = true;
            if (user == null){
                user = (User)session.getAttribute("user");
                resetPW = false;
            }
            UserWithPW userWithPW;
            if (user != null)
                userWithPW = UserDAO.getUserWithPW(connection,user.getUsername());
            else
                throw new SQLException("Null user when changing password");//skip to end, this is an exceptional case
            String oldPassword = req.getParameter("old_password");
            if (resetPW || (userWithPW != null && UserDAO.verifyUser(connection,userWithPW,oldPassword))){
                String password = req.getParameter("new_password");
                UserDAO.updatePassword(connection,user.getID(),password);
            } else {
                //Oh no! do nothing I guess
            }
            session.setAttribute("resetPW",null);
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        resp.sendRedirect("./home-page");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/frontend/edit-password.jsp").forward(req,resp);
    }
}
