package fr.mimifan.jac.wallpaper;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WallpaperManager {

    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(int uiAction, int uiParam, String pvParam, int fWinIni);
    }

    public static final int SPI_SETDESKWALLPAPER = 20;
    public static final int SPIF_UPDATEINIFILE = 0x01;
    public static final int SPIF_SENDCHANGE = 0x02;

    private static final WallpaperManager instance = new WallpaperManager();

    public void changeWallPaper() {
        try {
            BufferedImage image = ImageIO.read(new URL("http://5.135.74.201:1570/wallpaper"));
            File outputFile = new File("downloaded_image.jpg");
            ImageIO.write(image, "jpg", outputFile);


            boolean result = User32.INSTANCE.SystemParametersInfo(SPI_SETDESKWALLPAPER, 0, outputFile.getAbsolutePath(), SPIF_UPDATEINIFILE | SPIF_SENDCHANGE);
            if (!result) { System.err.println("Failed to change wallpaper.");}

            outputFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WallpaperManager getInstance() {
        return instance;
    }
}
