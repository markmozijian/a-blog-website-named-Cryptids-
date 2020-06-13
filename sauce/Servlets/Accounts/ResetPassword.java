package Servlets.Accounts;

import Tools.DBConnection;
import email.EmailSender;
import security.Token;
import security.TokenDAO;
import users.User;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/*
Password-email reset ritual
 */
public class ResetPassword extends HttpServlet {

    protected static final String subject = "Password reset token";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tokenString = req.getParameter("token");
        if (tokenString == null || tokenString.length() <= 0) {
            req.getRequestDispatcher("./WEB-INF/accounts/reset_password.jsp").forward(req, resp);
        }
        else {
            try (Connection connection = DBConnection.getDefault()){
                int token = Integer.parseInt(tokenString);
                if (TokenDAO.verifyToken(connection,token)){
                    int userID = TokenDAO.getUserID(connection,token);
                    TokenDAO.deleteToken(connection,token);
                    User user = UserDAO.getUser(connection,userID);
                    HttpSession session = req.getSession();
                    session.setAttribute("resetPW",user);
                    req.getRequestDispatcher("./WEB-INF/accounts/new_password.jsp").forward(req,resp);
                    return;
                }
            } catch (SQLException | NumberFormatException e){
                e.printStackTrace();
            }
            resp.sendRedirect("./home-page");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (username != null)
            req.setAttribute("username",username);
        try(Connection connection = DBConnection.getDefault()) {
            User user = UserDAO.getUser(connection,username);
            if (user != null){
                int token;
                do {
                    token = Token.getToken();
                } while (TokenDAO.tokenExists(connection,token));
                if (TokenDAO.userTokenExists(connection,user.getID())){
                    TokenDAO.deleteUserToken(connection,user.getID());
                }
                TokenDAO.addToken(connection,user.getID(),token);
                boolean success = EmailSender.sendEmail2User(user,subject,
                        Token.resetPWmessage(user.getUsername(),token));
                if (success){
                    System.out.println("Reset Email sent to " + user.getEmail());
                } else {
                    System.out.println("Reset Email send failed: " + user.getEmail());
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        req.setAttribute("resetted",1);
        req.getRequestDispatcher("./WEB-INF/accounts/reset_password.jsp").forward(req, resp);
    }
}
