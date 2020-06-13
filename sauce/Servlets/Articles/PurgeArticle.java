package Servlets.Articles;

import Tools.DBConnection;
import Tools.SessionTools;
import articles.Article;
import articles.ArticleDAO;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
With proper permissions, a user can rip an article from the database
 */
public class PurgeArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int articleID = Integer.parseInt(req.getParameter("articleID"));
            Article article = ArticleDAO.getArticle(connection,articleID);
            User author = article.getAuthor();
            User user = SessionTools.getSessionUser(req);
            req.setAttribute("article",article);
            if (user != null && (author.equals(user) || user.isAdmin())){
                req.getRequestDispatcher("./WEB-INF/articles/confirm_purge.jsp").forward(req,resp);
            } else {
                resp.sendRedirect("./view_article?articleID=" + articleID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            int articleID = Integer.parseInt(req.getParameter("articleID"));
            User author = ArticleDAO.getAuthor(connection,articleID);
            User user = SessionTools.getSessionUser(req);
            if (author.equals(user) || user.isAdmin()){
                ArticleDAO.purgeArticle(connection,articleID);
                resp.sendRedirect("./home-page");
            } else {
                resp.sendRedirect("./view_article?articleID=" + articleID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
