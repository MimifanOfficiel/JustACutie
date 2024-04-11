package fr.mimifan.jac.popups;

import fr.mimifan.jac.resources.ResourcesManager;

import java.util.Random;

public class PopupsManager {

    public static void load(){

        Random random = new Random();
        final int MAX_INTERVAL_MINUTES = 60;
        final int MAX_INTERVAL_MILLISECONDS = MAX_INTERVAL_MINUTES * 60 * 1000;

        while (true) {
            try {
                int intervalMilliseconds = random.nextInt(MAX_INTERVAL_MILLISECONDS) + 1;
                int index = random.nextInt(ResourcesManager.getInstance().getPopups().size()-1);
                new Popup(ResourcesManager.getInstance().getPopups().get(index));
                System.out.println("Next popup in " + (intervalMilliseconds / 1000) / 60 + " minutes");
                Thread.sleep(intervalMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
