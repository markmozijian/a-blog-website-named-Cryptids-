package Servlets.Gallery;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.ParseTools;
import articles.ArticleDAO;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
parse for images
 */
public class GetArticleImages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorIDStr = req.getParameter("author");
        try(Connection connection = DBConnection.getDefault()){
            String images,html;
            html = getHTML(connection,authorIDStr);
            images = ParseTools.extractIMG(html);
            JSONArray array = ParseTools.toJSONArray(images);
            JSONResponse.send(resp,array);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    protected static String getHTML(Connection connection, String authorIDStr) throws SQLException{
        String html;
        if (ParseTools.isInt(authorIDStr)){
            int authorID = Integer.parseInt(authorIDStr);
            html = ArticleDAO.getAuthorContent(connection,authorID);

        } else {
            html = ArticleDAO.getAllContent(connection);
        }
        return html;
    }
}
