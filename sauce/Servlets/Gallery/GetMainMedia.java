package Servlets.Gallery;

import javax.servlet.ServletException;
import java.io.File;
/*
get media paths
 */
public class GetMainMedia extends GetMainPictures {
    @Override
    public void init() throws ServletException {
        filePath = "assets/media";
        folder = new File(getServletContext().getRealPath(filePath));
        if (!folder.exists()){
            folder.mkdirs();
        }
    }
}
