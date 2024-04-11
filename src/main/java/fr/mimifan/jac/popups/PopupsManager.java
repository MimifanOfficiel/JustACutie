package fr.mimifan.jac.popups;

import fr.mimifan.jac.resources.ResourcesManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PopupsManager {

    private static final PopupsManager instance = new PopupsManager();
    private int nextInterval = 0;


    public void load(){
        Integer[] possibleTimes = {1, 5, 10, 20, 25, 27, 30, 35, 40, 45, 50, 55, 60, 80, 100};
        Random random = new Random();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nextInterval = random.nextInt(possibleTimes.length);
                System.out.println("Next popup in " + nextInterval + " minutes <3");
                int index = random.nextInt(ResourcesManager.getInstance().getPopups().size()-1);
                new Popup(ResourcesManager.getInstance().getPopups().get(index));
            }
        }, 0, possibleTimes[nextInterval]*60*1000L);
    }


    public static PopupsManager getInstance() {
        return instance;
    }
}
