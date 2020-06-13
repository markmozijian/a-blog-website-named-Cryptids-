package Servlets.Articles;

import Tools.DBConnection;
import Tools.ParseTools;
import Tools.SessionTools;
import articles.*;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/*
Shows a specific article
 */
public class ViewArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = DBConnection.getDefault()){
            String str = req.getParameter("articleID");
            if (!ParseTools.isInt(str)){
                resp.sendError(418);
                return;
            }
            int articleID = Integer.parseInt(str);
            Article article;
            User user = SessionTools.getSessionUser(req);
            User author = ArticleDAO.getAuthorIfCensored(connection,articleID);
            if (user != null && (user.isAdmin() || user.equals(author)) ){
                article = ArticleDAO.getArticleForAdmin(connection,articleID);
            } else {
                article = ArticleDAO.getArticle(connection, articleID);
            }
            List<Comment> comments = article.getComments();
            req.setAttribute("article",article);
            req.setAttribute("comments",comments);
            req.setAttribute("likes", LikeStatusDAO.getNumberOfLikes(connection,articleID));
            req.getRequestDispatcher("./WEB-INF/articles/view_article.jsp").forward(req,resp);
        } catch (SQLException e){
            e.printStackTrace();
            //dunno what to do
        } catch (NumberFormatException e){
            //No idea what to do
        }
    }
}
