package fr.mimifan.jac.wallpaper;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WallpaperManager {

    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(int uiAction, int uiParam, String pvParam, int fWinIni);
    }

    public static final int SPI_SETDESKWALLPAPER = 20;
    public static final int SPIF_UPDATEINIFILE = 0x01;
    public static final int SPIF_SENDCHANGE = 0x02;


    public static void changeWallPaper() {
        while (true) {

            try {
                BufferedImage image = ImageIO.read(new URL("http://5.135.74.201:1570/wallpaper"));
                File outputFile = new File("downloaded_image.jpg");
                ImageIO.write(image, "jpg", outputFile);


                boolean result = User32.INSTANCE.SystemParametersInfo(SPI_SETDESKWALLPAPER, 0, outputFile.getAbsolutePath(), SPIF_UPDATEINIFILE | SPIF_SENDCHANGE);
                if (!result) { System.err.println("Failed to change wallpaper.");}

                if(!outputFile.delete()) {
                    String message = "Sorry- I couldn't delete this image >..<";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Unable to delete file",
                            JOptionPane.ERROR_MESSAGE);
                }

                Thread.sleep(5 * 60 * 1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
