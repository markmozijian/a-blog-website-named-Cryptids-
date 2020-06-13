package Servlets.Articles;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.SessionTools;
import articles.Article;
import articles.ArticleDAO;
import org.json.simple.JSONArray;
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
logged in users can like an article
 */
public class LikedArticles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionTools.getSessionUser(req);
        if (user != null){
            try(Connection connection = DBConnection.getDefault()){
                List<Article> articleList = ArticleDAO.getLikedArticles(connection,user.getID());
                JSONArray array = AllArticles.megaJSONArray(
                        AllArticles.getMegaList(articleList));
                JSONResponse.send(resp,array);
                return;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("./all_articles").forward(req,resp);
    }
}
