package Tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
/*
Servlet context
 */
public class PathFinder implements ServletContextListener {
    private static ServletContext servletContext;

    public static final String mainPicPath = "assets/mainPics";
    public static final String avatarPath = "assets/avatars";

    public static ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        System.out.println("servlet context loaded");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Oh no!");
    }

    public static boolean checkFileExists(String path){
        File file = new File(getServletContext().getRealPath(path));
        return file.exists();
    }

    public static String existsElse(String path, String main, String def){
        boolean exists = checkFileExists(path + "/" + main);
        if (!exists){
            return def;
        }
        return main;
    }


}
