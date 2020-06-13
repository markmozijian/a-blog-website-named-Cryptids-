package Servlets.Articles;

import Tools.DBConnection;
import Tools.PathFinder;
import Tools.SQLtools;
import Tools.SessionTools;
import articles.Article;
import articles.ArticleDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import users.User;
import users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
/*
With proper permissions, a user can make a new article
 */
public class NewArticle extends HttpServlet {

    protected File uploadsFolder;
    protected String mainPicPath;
    protected File tempFolder;
    protected String mediaPath;
    protected File mediaFolder;

    @Override
    public void init() throws ServletException {
        super.init();

        this.mainPicPath = PathFinder.mainPicPath;
        this.mediaPath = "assets/media";

        this.uploadsFolder = new File(getServletContext().getRealPath(mainPicPath));
        checkExist(uploadsFolder);

        this.mediaFolder = new File(getServletContext().getRealPath(mediaPath));
        checkExist(mediaFolder);

        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
        checkExist(tempFolder);
    }

    protected void checkExist(File folder){
        if (!folder.exists()){
            folder.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("./WEB-INF/articles/new_article.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String title = null;
        String content = null;
        String picType,mediaType;
        String picName = null;
        User author = SessionTools.getSessionUser(req);
        String mediaName = null;
        Integer articleID = null;
        if (author == null)
            resp.sendRedirect("./login");
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            File fullsizeImageFile = null;
            File media = null;

            for (FileItem fi : fileItems) {

                if (fi.isFormField()) {
                    String name = fi.getFieldName();
                    String value = new String(fi.get(), StandardCharsets.UTF_8);
                    switch (name) {
                        case "title":
                            title = value;
                            break;
                        case "content":
                            content = value;
                            break;
                        case "articleID":
                            articleID = Integer.parseInt(value);
                            break;
                    }
                } else if ((fi.getContentType().startsWith("image/"))) {
                    picType = fi.getContentType().substring(6);
                    try(Connection connection = DBConnection.getDefault()){
                        int articlesAuthored = UserDAO.articlesWrittenAmount(connection,author.getID());
                        picName = String.format("%d_%d.%s",author.getID(),articlesAuthored,picType);
                    } catch (SQLException e){
                        e.printStackTrace();
                        resp.sendRedirect("./home-page");
                    }
                    picName = makeUnique(uploadsFolder,picName,"picture");
                    fullsizeImageFile = new File(uploadsFolder,picName);
                    fi.write(fullsizeImageFile);
                } else if ((fi.getContentType().startsWith("audio/")) || fi.getContentType().startsWith("video/")){
                    mediaType = fi.getContentType().substring(6);
                    mediaName = String.format("%d_0.%s",author.getID(),mediaType);
                    mediaName = makeUnique(mediaFolder,mediaName,"media");
                    media = new File(mediaFolder,mediaName);
                    fi.write(media);
                }
            }
        } catch (Exception e){
            throw new ServletException(e);
        }
        saveArticle(req,resp,author,title,content,picName,mediaName,articleID);
    }
    protected void saveArticle(HttpServletRequest req, HttpServletResponse resp,User author, String title, String content, String picName,
                               String mediaName, Integer articleID) throws ServletException, IOException{
        Article article = new Article(author,title,content);
        if (picName != null)
            article.setMainPicture(picName);
        if (mediaName != null)
            article.setMainMedia(mediaName);
        try(Connection connection = DBConnection.getDefault()){
            ArticleDAO.addNewArticle(connection,article);
        } catch (SQLException e){
            SQLtools.squeal(e,req,resp);
            return;
        }
        resp.sendRedirect("./home-page");
    }
    protected String makeUnique(File folder, String fileName, String type) throws SQLException,IOException{
        File file = new File(folder,fileName);
        try(Connection connection = DBConnection.getDefault()) {
            while (file.exists() || checkDatabase(connection,fileName,type)) {
                String[] splitFile = fileName.split("_");//[authorId,filename]
                String authorID = splitFile[0];
                String fileDotType = splitFile[1];
                String[] number_type = fileDotType.split("[.]");//[number,file type]
                String fileNumber = number_type[0];
                String fileType = number_type[1];
                int number;
                try {
                    number =Integer.parseInt(fileNumber) + 1;// different from old name
                } catch (NumberFormatException e){
                    number = 0;
                }
                fileName = String.format("%s_%d.%s", authorID, number,fileType);
                file = new File(folder,fileName);
            }
            return fileName;
        }
    }
    protected boolean checkDatabase(Connection connection,String fileName,String type) throws SQLException{
        //if it exists it should keep going
        switch (type){
            case "picture":
                return ArticleDAO.mainPicCollision(connection,fileName);//returns true if exists
            case "media":
                return ArticleDAO.mediaCollision(connection,fileName);
        }
        return false;
    }
}
