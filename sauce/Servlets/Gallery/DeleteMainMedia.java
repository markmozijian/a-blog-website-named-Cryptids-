package Servlets.Gallery;

import javax.servlet.ServletException;
/*
delete a file, a video from server
 */
public class DeleteMainMedia extends DeleteMainPic {
    @Override
    public void init() throws ServletException {
        path = "/assets/media/";
    }
}
