package Servlets.Comments;

import Tools.DBConnection;
import articles.LikeStatusDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LikeStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(Connection connection = DBConnection.getDefault()){
            int articleID = Integer.parseInt(req.getParameter("articleID"));
            int userID = Integer.parseInt(req.getParameter("userID"));
          int status=  LikeStatusDAO.getLikeIcon(connection,userID,articleID);
            resp.getWriter().write(status+"");

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
