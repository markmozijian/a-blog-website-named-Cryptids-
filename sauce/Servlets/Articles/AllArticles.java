package Servlets.Articles;

import Tools.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
Returns JSON representing articles
 */
public class AllArticles extends HttpServlet {
    public static final int rows = 3;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDefault()){
            String my = req.getParameter("my");
            List<Article> articleList;
            if (ParseTools.isInt(my)) {
                int authorID = Integer.parseInt(my);
                articleList = ArticleDAO.getArticlesByAuthor(connection,authorID);
                User user = SessionTools.getSessionUser(req);
                if (user == null || user.getID() != authorID){
                    articleList.removeIf(a -> (Redacted.getUser().equals(a.getAuthor())));
                }
            } else {
                articleList = ArticleDAO.getAllArticles(connection);
            }
            Collections.sort(articleList);
            List<List<Article>> megaList = getMegaList(articleList);
            JSONArray megaArray = megaJSONArray(megaList);
            JSONResponse.send(resp,megaArray);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    public static List<List<Article>> getMegaList(List<Article> articleList){
        List<List<Article>> megaList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            megaList.add(new ArrayList<>());
        }
        for (int i = 0; i < articleList.size(); i++) {
            megaList.get(i%3).add(articleList.get(i));
        }
        return megaList;
    }
    public static JSONArray megaJSONArray(List<List<Article>> megaList){
        JSONArray megaArray = new JSONArray();
        for (List<Article> articleList:
             megaList) {
            JSONArray miniArray = JSONify(articleList);
            megaArray.add(miniArray);
        }
        return megaArray;
    }
    public static JSONArray JSONify(List<Article> articleList){
        JSONArray array = new JSONArray();
        for (Article article:
             articleList) {
            array.add(article.toJSON());
        }
        return array;
    }
}
