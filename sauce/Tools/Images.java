package Tools;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/*
thumbnail creation
 */
public class  Images {
    public static BufferedImage makeThumbnail(File imageFile) throws IOException {
        //BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        //img.createGraphics().drawImage(ImageIO.read(imageFile).getScaledInstance(200, 200, Image.SCALE_SMOOTH), 0, 0, null);
        BufferedImage resizeMe = ImageIO.read(imageFile);
        Dimension newMaxSize = new Dimension(200, 200);
        return Scalr.resize(resizeMe, Scalr.Method.QUALITY, newMaxSize.width, newMaxSize.height);
    }
    public static String storeThumbnail(File imageFile, int userID, File outputDirectory, String type) throws IOException {
        BufferedImage thumbnail = makeThumbnail(imageFile);
        return storeImage(thumbnail,""+userID,outputDirectory, type);
    }
    public static String storeImage(BufferedImage image, String imageName, File outputDirectory, String type) throws IOException{
        String filename = imageName + "." + type;
        String path = outputDirectory.getCanonicalPath() + File.separator + filename;
        File file = new File(path);
        ImageIO.write(image, type, file);
        return filename;
    }
}
