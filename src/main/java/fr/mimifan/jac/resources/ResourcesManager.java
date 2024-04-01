package fr.mimifan.jac.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResourcesManager {

    private static final ResourcesManager instance = new ResourcesManager();
    private List<BufferedImage> popups = new ArrayList<>();

    public void loadPopups() {
        try {
            File folder = new File(Objects.requireNonNull(getClass().getResource("/popups")).toURI());
            for (String s : Objects.requireNonNull(folder.list())) {
                File file = new File(Objects.requireNonNull(getClass().getResource("/popups/" + s)).toURI());
                BufferedImage image = ImageIO.read(file);
                popups.add(image);
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<BufferedImage> getPopups() { return popups; }
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
