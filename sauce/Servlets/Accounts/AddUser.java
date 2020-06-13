package Servlets.Accounts;

import Tools.DBConnection;
import Tools.SessionTools;
import users.User;
import users.UserDAO;

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
Admin adds user, new user can be an admin
 */
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionTools.getSessionUser(req);
        if (user != null && user.isAdmin()) {
            req.getRequestDispatcher("./WEB-INF/accounts/add_account.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("./signup");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User admin = SessionTools.getSessionUser(req);
        if (admin != null && admin.isAdmin()) {
            try (Connection connection = DBConnection.getDefault()) {
                int id = Integer.parseInt(req.getParameter("id"));
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User user = new User(id, username);
                UserDAO.addNewAdmin(connection,user,password);
            } catch (SQLException | NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("./home-page");
    }
}
