package Servlets.Comments;

import articles.ArticleDAO;
import articles.Comment;
import articles.CommentDAO;
import users.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
/*
user can delete a comment
 */
public class DeleteComment extends EditComment {

    @Override
    protected void action(HttpServletRequest req, Connection connection, Comment comment, User commenter, User user, int articleID) throws SQLException {
        User articleAuthor = ArticleDAO.getAuthor(connection,articleID);
        int commentID = comment.getID();
        if (commenter.equals(user) || articleAuthor.equals(user) || user.isAdmin()){
            CommentDAO.softDelete(connection,commentID,user.getUsername());
        }
    }
}
