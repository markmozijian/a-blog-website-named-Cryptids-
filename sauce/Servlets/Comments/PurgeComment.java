package Servlets.Comments;

import Tools.DBConnection;
import Tools.SessionTools;
import articles.ArticleDAO;
import articles.Comment;
import articles.CommentDAO;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
Rip comment out of the database
 */
public class PurgeComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int commentID = Integer.parseInt(req.getParameter("commentID"));
            Comment comment = CommentDAO.getComment(connection,commentID);
            User commenter = comment.getAuthor();
            int articleID = comment.getArticleID();
            User user = SessionTools.getSessionUser(req);
            User articleAuthor = ArticleDAO.getAuthor(connection,articleID);
            if (user.isAdmin() || commenter.equals(user) || articleAuthor.equals(user)){
                CommentDAO.purgeComment(connection,commentID);
            }
            resp.sendRedirect("./view_article?articleID=" + articleID);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
