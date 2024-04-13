package fr.mimifan.jac.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ResourcesManager {

    private static final ResourcesManager instance = new ResourcesManager();

    public BufferedImage getBufferedImage(String path) {
        BufferedImage image;
        InputStream stream = ResourcesManager.class.getResourceAsStream(path);

        if (stream == null) {
            System.out.println("Resource not found: " + path);
            return null;
        }

        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return image;
    }

    public static ResourcesManager getInstance() {
        return instance;
    }
}
