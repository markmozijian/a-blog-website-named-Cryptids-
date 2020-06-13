package Tools;

import org.json.simple.JSONAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
Endpoint
 */
public class JSONResponse {
    public static void setup(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
    public static void send(HttpServletResponse response, JSONAware j) {
        setup(response);

        try {
            response.getWriter().print(j.toJSONString());
        } catch (IOException e) {
            // Error communicating with client, can't really recover from this gracefully
            e.printStackTrace();
        }
    }
    public static void send(HttpServletResponse response, String text){
        setup(response);

        try {
            response.getWriter().print(text);
        } catch (IOException e) {
            // Error communicating with client, can't really recover from this gracefully
            e.printStackTrace();
        }
    }

}
