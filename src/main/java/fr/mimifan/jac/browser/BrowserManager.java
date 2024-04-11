package fr.mimifan.jac.browser;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowserManager {

    private static final BrowserManager instance = new BrowserManager();

    public void openLink(String url) {

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


    public static BrowserManager getInstance() {
        return instance;
    }
}
