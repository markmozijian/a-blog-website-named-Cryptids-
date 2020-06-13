package Servlets.Comments;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.ParseTools;
import Tools.SessionTools;
import articles.Article;
import articles.ArticleDAO;
import articles.Comment;
import articles.CommentDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
load comments in view_article
 */
public class GetComments extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String articleStr = req.getParameter("article");
        if (ParseTools.isInt(articleStr)){
            int articleID = Integer.parseInt(articleStr);
            try(Connection connection = DBConnection.getDefault()){
                Article article = ArticleDAO.getArticle(connection,articleID);
                JSONArray comments = article.getJSONComments();
                JSONResponse.send(resp,comments);
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            String commentStr = req.getParameter("comment");
            User user = SessionTools.getSessionUser(req);
            if (ParseTools.isInt(commentStr) && user != null && user.isAdmin()){
                int commentID = Integer.parseInt(commentStr);
                try(Connection connection = DBConnection.getDefault()){
                    Comment comment = CommentDAO.getCommentForAdmin(connection, commentID);
                    JSONResponse.send(resp,comment.toJSON());
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
