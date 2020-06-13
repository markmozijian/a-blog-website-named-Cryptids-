package Servlets.Accounts;

import Tools.DBConnection;
import Tools.SessionTools;
import users.User;
import users.UserDAO;
import users.UserWithPW;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
/*
User can destroy their own account
 */
public class DestroyOwnAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = DBConnection.getDefault()){
            User user = SessionTools.getSessionUser(req);
            UserWithPW userWithPW = UserDAO.getUserWithPW(connection,user.getID());
            if (userWithPW == null)
                resp.sendRedirect("./home-page");
            String password = req.getParameter("password");
            if (UserDAO.verifyUser(connection,userWithPW,password)){
                UserDAO.murder(connection,user.getID());
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        resp.sendRedirect("./logout");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/accounts/confirm_suicide.jsp").forward(req,resp);
    }
}
