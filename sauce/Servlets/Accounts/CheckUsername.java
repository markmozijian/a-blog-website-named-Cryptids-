package Servlets.Accounts;

import Tools.DBConnection;
import Tools.JSONResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
/*
Check if a username is takne
 */
public class CheckUsername extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        try(Connection connection = DBConnection.getDefault()){
            JSONResponse.setup(resp);
            PrintWriter writer = resp.getWriter();
            writer.print(UserDAO.usernameExists(connection,username));
            writer.flush();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
