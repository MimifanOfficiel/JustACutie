package fr.mimifan.jac.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourcesManager {

    private static final ResourcesManager instance = new ResourcesManager();
    private List<BufferedImage> popups = new ArrayList<>();

    public void loadPopups() {
        String packagePath = "popups";

        try (JarFile jarFile = new JarFile("JustA_Cutie-1.0-SNAPSHOT-jar-with-dependencies.jar")) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith(packagePath) && !entry.isDirectory()) {
                    InputStream inputStream = ResourcesManager.class.getResourceAsStream("/"+name);
                    if (inputStream != null) {
                        BufferedImage image = ImageIO.read(inputStream);
                        popups.add(image);
                        if (image != null) {
                            popups.add(image);
                        } else { System.err.println("Failed to read image: " + name); }
                    } else { System.err.println("InputStream is null for: " + name); }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
