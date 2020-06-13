package Servlets.Comments;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.ParseTools;
import articles.LikeStatusDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
get how many likes an article has
 */
public class GetLikeCount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("articleID");
        if (ParseTools.isInt(idStr)){
            int articleID = Integer.parseInt(idStr);
            try(Connection connection = DBConnection.getDefault()){
                int likes = LikeStatusDAO.getNumberOfLikes(connection,articleID);
                JSONResponse.send(resp,likes + "");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
