package Tools;

import users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
Get user from session
 */
public class SessionTools {
    public static User getSessionUser(HttpServletRequest req){
        HttpSession session = req.getSession();
        return (User) session.getAttribute("user");
    }
}
