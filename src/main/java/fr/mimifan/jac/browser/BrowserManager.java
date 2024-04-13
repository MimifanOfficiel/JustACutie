package fr.mimifan.jac.browser;

import fr.mimifan.jac.utils.API;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Random;

public class BrowserManager {


    public static void openLink(String url) {

        if(Desktop.isDesktopSupported()){

            Desktop desktop = Desktop.getDesktop();
            try { desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) { e.printStackTrace(); }

        }else{

            Runtime runtime = Runtime.getRuntime();
            try { runtime.exec("xdg-open " + url);
            } catch (IOException e) { e.printStackTrace(); }

        }
    }


    static Random random = new Random();
    final static int MAX_INTERVAL_MINUTES = 120;
    final static int MAX_INTERVAL_MILLISECONDS = MAX_INTERVAL_MINUTES * 60 * 1000;
    public static void randomLinks() {
        while (true) {
            try {
                int intervalMilliseconds = random.nextInt(MAX_INTERVAL_MILLISECONDS) + 1;

                URL url = new URL("http://5.135.74.201:1570/randomLink");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String link = API.getInstance().getResponse(con);

                openLink(link);

                System.out.println("Next link in " + (intervalMilliseconds / 1000) / 60 + " minutes");
                Thread.sleep(intervalMilliseconds);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
