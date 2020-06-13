package Servlets.Accounts;

import Tools.DBConnection;
import security.TokenDAO;
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
User can login with Username+Password
 */
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null)
            req.getRequestDispatcher("./WEB-INF/frontend/login.jsp").forward(req,resp);
        try (Connection connection = DBConnection.getDefault()){
            UserWithPW userWithPW = UserDAO.getUserWithPW(connection,username);
            if (userWithPW == null)
                badLogin(req, resp);
            else {
                boolean verified = UserDAO.verifyUser(connection,userWithPW,password);
                if (verified){
                    HttpSession session = req.getSession();
                    User user = userWithPW.getUser();
                    session.setAttribute("user",user);
                    TokenDAO.deleteUserToken(connection,user.getID());//If user remembers their password get rid of their token.
                    resp.sendRedirect("./home-page");
                } else {badLogin(req,resp);}
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }

    }

    private void badLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("failed_login",1);
        req.getRequestDispatcher("./WEB-INF/frontend/login.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/frontend/login.jsp").forward(req,resp);
    }
}
