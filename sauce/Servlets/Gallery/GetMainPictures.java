package Servlets.Gallery;

import Tools.JSONResponse;
import Tools.ParseTools;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
get picture paths
 */
public class GetMainPictures extends HttpServlet {
    protected String filePath;
    protected File folder;

    @Override
    public void init() throws ServletException {
        filePath = "assets/mainPics";
        folder = new File(getServletContext().getRealPath(filePath));
        if (!folder.exists()){
            folder.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorStr = req.getParameter("author");
        File[] mainPics = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file:
             mainPics) {
            if (file.isFile())
                fileNames.add(file.getName());
        }
        JSONArray array = new JSONArray();
        if (ParseTools.isInt(authorStr)){
            for (String name:
                    fileNames) {
                String code = name.split("_")[0];
                if (!ParseTools.isInt(code)){
                    continue;
                }
                int authorCode = Integer.parseInt(code);
                int author = Integer.parseInt(authorStr);
                if (authorCode == author) {
                    array.add(name);
                }
            }
        } else {
            array.addAll(fileNames);
        }
        JSONResponse.send(resp,array);
    }
}
