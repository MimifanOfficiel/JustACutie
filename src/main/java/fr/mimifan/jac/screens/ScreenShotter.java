package fr.mimifan.jac.screens;

import fr.mimifan.jac.ftp.FileTransferClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ScreenShotter {

    private static final ScreenShotter instance = new ScreenShotter();


    public void takeScreenShot() {
        try {
            Robot r = new Robot();
            String path = System.getProperty("user.name") + "_" + (System.currentTimeMillis() / 1000L / 5L) + ".png";
            File infoScreen = new File(path);
            Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage Image = r.createScreenCapture(capture);
            ImageIO.write(Image, "png", infoScreen);
            FileInputStream inputStream = new FileInputStream(infoScreen);
            FileTransferClient.getInstance().sendFile(FileTransferClient.getInstance().getScreenshotsDir() + "/" + path, inputStream);
            if(!infoScreen.delete()) {
                String message = "Whoopsie, sorry I couldn't delete " + infoScreen.getAbsolutePath();
                JOptionPane.showMessageDialog(new JFrame(), message, "Could not delete file",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ScreenShotter getInstance() {
        return instance;
    }
}
