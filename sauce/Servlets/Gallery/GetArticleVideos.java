package Servlets.Gallery;

import Tools.DBConnection;
import Tools.JSONResponse;
import Tools.ParseTools;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
parse for videos
 */
public class GetArticleVideos extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorIDStr = req.getParameter("author");
        try(Connection connection = DBConnection.getDefault()){
            String vids,html;
            html = GetArticleImages.getHTML(connection,authorIDStr);
            vids = ParseTools.extractEmbedded(html);
            JSONArray array = ParseTools.toJSONArray(vids);
            JSONResponse.send(resp,array);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
