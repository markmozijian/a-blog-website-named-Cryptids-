package Servlets.Comments;

import Tools.DBConnection;
import Tools.SessionTools;
import articles.LikeStatusDAO;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LikeIcon extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



            try(Connection connection = DBConnection.getDefault()){
                int articleID = Integer.parseInt(req.getParameter("articleID"));
                int userID = Integer.parseInt(req.getParameter("userID"));
                int status = Integer.parseInt(req.getParameter("status"));
                User user = SessionTools.getSessionUser(req) ;
                if(user != null && user.getID() == userID){//added by jonathan for security
                    int newStatus = LikeStatusDAO.setLikeIcon(connection, articleID, userID, status);
                    resp.getWriter().write(String.valueOf(newStatus));
                }

            } catch (SQLException e){
                e.printStackTrace();
            }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
