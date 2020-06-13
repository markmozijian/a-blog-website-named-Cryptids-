package Servlets.Comments;

import Tools.DBConnection;
import Tools.SessionTools;
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
user can edit a comment
 */
public class EditComment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int commentID = Integer.parseInt(req.getParameter("commentID"));
            Comment comment = CommentDAO.getComment(connection,commentID);
            User commenter = comment.getAuthor();
            User user = SessionTools.getSessionUser(req);
            int articleID = comment.getArticleID();
            action(req,connection,comment,commenter,user,articleID);
            resp.sendRedirect("./view_article?articleID=" + articleID);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void action(HttpServletRequest req, Connection connection, Comment comment, User commenter, User user, int articleID) throws SQLException{
        int commentID = comment.getID();
        if (commenter.equals(user)){
            String content = req.getParameter("content");
            if (content != null)
                CommentDAO.editComment(connection,commentID,content);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
