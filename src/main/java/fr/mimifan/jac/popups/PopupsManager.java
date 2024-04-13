package fr.mimifan.jac.popups;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class PopupsManager {

    public static void load(){

        Random random = new Random();
        final int MAX_INTERVAL_MINUTES = 60;
        final int MAX_INTERVAL_MILLISECONDS = MAX_INTERVAL_MINUTES * 60 * 1000;

        while (true) {
            try {
                int intervalMilliseconds = random.nextInt(MAX_INTERVAL_MILLISECONDS) + 1;

                BufferedImage image = ImageIO.read(new URL("http://5.135.74.201:1570/wallpaper"));
                if(image != null) {

                    new Popup(image);

                    System.out.println("Next popup in " + (intervalMilliseconds / 1000) / 60 + " minutes");
                    Thread.sleep(intervalMilliseconds);

                } else {
                    String message = "Seems like you weren't able to retrieve a popup";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Could not get popup",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
