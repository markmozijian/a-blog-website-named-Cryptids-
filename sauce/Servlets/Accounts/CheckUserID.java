package Servlets.Accounts;

import Tools.DBConnection;
import Tools.JSONResponse;
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
Check if a userID is taken
 */
public class CheckUserID extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionTools.getSessionUser(req);
        String idStr = req.getParameter("id");
        if (user != null && user.isAdmin() && ParseTools.isInt(idStr)){
            int id = Integer.parseInt(idStr);
            try(Connection connection = DBConnection.getDefault()){
                User checkee = UserDAO.getUser(connection,id);
                boolean exists = (checkee != null);
                JSONResponse.send(resp,exists + "");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
}
