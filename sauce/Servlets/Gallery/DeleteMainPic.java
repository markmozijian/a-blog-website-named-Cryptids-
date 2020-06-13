package Servlets.Gallery;

import Tools.ParseTools;
import Tools.SessionTools;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
/*
delete a picture from server
 */
public class DeleteMainPic extends HttpServlet {
    protected String path;
    public final String DEFAULT_PIC = "main-bg.jpg";

    @Override
    public void init() throws ServletException {
        path = "/assets/mainPics/";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        User user = SessionTools.getSessionUser(req);
        if (fileName != null && fileName.length() > 0 && user != null && !fileName.contains(DEFAULT_PIC)){
            String codeStr = fileName.split("_")[0];
            if (ParseTools.isInt(codeStr)) {
                int code = Integer.parseInt(codeStr);
                if (code == user.getID()  || user.isAdmin()) {
                    String filePath = getServletContext().getRealPath(path + fileName);
                    File file = new File(filePath);
                    if (file.exists()){
                        file.delete();
                    }
                }
            }
        }
        resp.sendRedirect("./gallery");
    }
}
