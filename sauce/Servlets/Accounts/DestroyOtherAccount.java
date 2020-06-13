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
Admin can destroy another account
 */
public class DestroyOtherAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int victimID = Integer.parseInt(req.getParameter("victimID"));
            User admin = SessionTools.getSessionUser(req);
            UserWithPW redButton = UserDAO.getUserWithPW(connection,admin.getID());
            String password = req.getParameter("password");
            if (admin.isAdmin() && UserDAO.verifyUser(connection,redButton,password)){
                UserDAO.murder(connection,victimID);
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        resp.sendRedirect("./logout");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int victimID = Integer.parseInt(req.getParameter("victimID"));
            User victim = UserDAO.getUser(connection,victimID);
            req.setAttribute("victim",victim);
            req.getRequestDispatcher("./WEB-INF/accounts/murder_confirm.jsp").forward(req,resp);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
