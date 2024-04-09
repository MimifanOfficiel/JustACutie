package fr.mimifan.jac.popups;

import javax.swing.*;
import java.util.Timer;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class Popup {

    public Popup(BufferedImage image) {
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);

        JFrame frame = new JFrame("Cutie<3");
        frame.setIconImage(image);
        frame.setUndecorated(true); // Set undecorated before making it visible
        frame.getContentPane().add(imageLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fadeInAndOut(frame); // Start fade in and out effect after configuring the frame

        frame.setVisible(true); // Make the frame visible at the end
    }

    private static void fadeInAndOut(JFrame frame) {
        Timer timer = new Timer();
        TimerTask fadeInTask = new TimerTask() {
            float opacity = 0.0f;
            final float increment = 0.05f; // Increment for opacity change
            //Miss Rubes owns my pc
            @Override
            public void run() {
                if (opacity < 1.0f) {
                    opacity += increment;
                    if (opacity > 1.0f) {opacity = 1.0f;}
                    frame.setOpacity(opacity);
                } else {
                    timer.cancel();
                    Timer fadeOutTimer = new Timer(); // Create a new Timer for fade out
                    TimerTask fadeOutTask = new TimerTask() {
                        float opacity = 1.0f;

                        @Override
                        public void run() {
                            if (opacity > 0.0f) {
                                opacity -= increment;
                                if (opacity < 0.0f) {
                                    opacity = 0.0f; // Ensure opacity stays within valid range
                                }
                                frame.setOpacity(opacity);
                            } else {
                                fadeOutTimer.cancel(); // Cancel the fade-out Timer
                                frame.dispose(); // Dispose the frame when fade-out is complete
                            }
                        }
                    };
                    fadeOutTimer.scheduleAtFixedRate(fadeOutTask, 0, 200);
                }
            }
        };
        timer.scheduleAtFixedRate(fadeInTask, 0, 100);
    }




}
