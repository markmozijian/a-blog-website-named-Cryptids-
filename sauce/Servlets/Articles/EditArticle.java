package Servlets.Articles;

import Tools.DBConnection;
import Tools.SQLtools;
import articles.Article;
import articles.ArticleDAO;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
With proper permissions, a user can edit an article
 */
public class EditArticle extends NewArticle {

    @Override
    protected void saveArticle(HttpServletRequest req, HttpServletResponse resp, User author, String title, String content, String picName, String mediaName, Integer articleID) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            Article article = ArticleDAO.getArticleForAdmin(connection,articleID);
            User authorOfArticle = article.getAuthor();
            if (author.equals(authorOfArticle)){
                article.setTitle(title);
                article.setContent(content);
                if (picName != null)
                    article.setMainPicture(picName);
                if (mediaName != null)
                    article.setMainMedia(mediaName);
                ArticleDAO.updateArticle(connection,article);
                resp.sendRedirect("./view_article?articleID=" + articleID);
            } else {
                req.setAttribute("action_denied",1);
                req.getRequestDispatcher("./view_article?articleID=" + articleID);
            }
        } catch (SQLException e){
            SQLtools.squeal(e,req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()) {
            int articleID = Integer.parseInt(req.getParameter("articleID"));
            Article article = ArticleDAO.getArticleForAdmin(connection,articleID);
            req.setAttribute("article",article);
            req.getRequestDispatcher("./WEB-INF/articles/new_article.jsp").forward(req,resp);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (NumberFormatException e){
            //Not sure maybe redirect somewhere
            resp.sendRedirect("./home-page");
        }
    }
}
