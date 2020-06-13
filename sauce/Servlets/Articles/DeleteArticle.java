package Servlets.Articles;

import Tools.DBConnection;
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
With proper permissions, a user can delete an article
 */
public class DeleteArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int articleID;
        try (Connection connection = DBConnection.getDefault()){
            articleID = Integer.parseInt(req.getParameter("articleID"));
            Article article = ArticleDAO.getArticle(connection,articleID);
            HttpSession session = req.getSession(false);
            User user = (User)session.getAttribute("user");
            if (article.getAuthor().equals(user) || user.isAdmin()){
                ArticleDAO.softDelete(connection,articleID,user.getUsername());
            }
            resp.sendRedirect("./home-page");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (NumberFormatException e){
            //not sure what to do
        } catch (NullPointerException e){
            //Still not sure what do do yet
        }
    }
}
