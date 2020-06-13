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
User can reply to a comment
 */
public class CommentOnComment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = DBConnection.getDefault()) {
            int articleID = Integer.parseInt(req.getParameter("articleID"));
            int parent = Integer.parseInt(req.getParameter("parent"));
            String content = req.getParameter("comment");
            User author = SessionTools.getSessionUser(req);
            if (author != null) {
                Comment comment = new Comment(author, content, articleID);
                comment.setParent(parent);
                CommentDAO.addComment(connection, comment);
            }
            resp.sendRedirect("./view_article?articleID=" + articleID);
        } catch (NumberFormatException e){
            //oh no!
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }

    }
}
