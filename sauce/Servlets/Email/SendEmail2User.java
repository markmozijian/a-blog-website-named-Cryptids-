package Servlets.Email;

import Tools.DBConnection;
import Tools.SessionTools;
import email.EmailSender;
import users.User;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
Send a customised email to a user if they have an email registered
 */
public class SendEmail2User extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String content = req.getParameter("content");
        String subject = req.getParameter("subject");
        try(Connection connection = DBConnection.getDefault()){
            User recipient = UserDAO.getUser(connection,username);
            User admin = SessionTools.getSessionUser(req);
            if (recipient != null && admin != null && admin.isAdmin()){
                EmailSender.sendEmail2User(recipient,subject,content);
                req.setAttribute("sent",1);
            }else{
                req.setAttribute("failed",1);
            }
            req.getRequestDispatcher("./home-page").forward(req,resp);
        } catch (SQLException e){
            e.printStackTrace();
            resp.sendError(418);
        }


    }
}
