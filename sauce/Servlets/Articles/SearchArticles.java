package Servlets.Articles;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.SessionTools;
import articles.Article;
import articles.ArticleDAO;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
Can search for articles containing keywords
 */
public class SearchArticles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        User user = SessionTools.getSessionUser(req);
        List<Article> articleList;
        if (search != null){
            try(Connection connection = DBConnection.getDefault()) {
                if (user != null && user.isAdmin()) {
                    articleList = ArticleDAO.searchArticleAsAdmin(connection,search);
                } else {
                    articleList = ArticleDAO.searchArticle(connection,search);
                }
            } catch (SQLException e){
                throw new ServletException(e);
            }
            articleList = checkArticle(articleList,search);
            JSONArray array = AllArticles.megaJSONArray(
                    AllArticles.getMegaList(articleList));
            JSONResponse.send(resp,array);
        } else {
            req.getRequestDispatcher(".//all_articles").forward(req,resp);
        }
    }
    public static List<Article> checkArticle(List<Article> articleList, String search){
        List<Article> list = new ArrayList<>();
        for (Article article:
             articleList) {
            String content = Jsoup.clean(article.getContent(), Whitelist.relaxed());
            if (content.toLowerCase().contains(search.toLowerCase())){
                list.add(article);
            }
        }
        return list;
    }
}
