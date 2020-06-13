package Servlets.Censor;

import Tools.DBConnection;
import Tools.ParseTools;
import Tools.Redacted;
import Tools.SessionTools;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
censor something
 */
public class Redact extends HttpServlet {
    //syntax  censor?type=X&id=Y
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionTools.getSessionUser(req);
        String idStr = req.getParameter("id");
        if (user != null && user.isAdmin() && ParseTools.isInt(idStr)) {
            String type = req.getParameter("type");
            try (Connection connection = DBConnection.getDefault()) {
                int id = Integer.parseInt(idStr);
                decide(connection, type, id);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("./home-page");
    }
    protected void decide(Connection connection, String type, int id) throws SQLException{
        switch (type){
            case "article":
                Redacted.redactArticle(connection,id);
                break;
            case "comment":
                Redacted.redactComment(connection,id);
                break;
        }
    }
}
