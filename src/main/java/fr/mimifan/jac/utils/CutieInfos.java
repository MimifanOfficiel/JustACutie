package fr.mimifan.jac.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class CutieInfos {

    public static String hostname = "";
    public String appDataFolder = System.getenv("APPDATA");
    public String discordSentryPath = appDataFolder + "/discord/sentry";
    public File jsonFile = new File(discordSentryPath + "/scope_v3.json");
    public static String cutiesDiscordName = "NotFound";

    public CutieInfos() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(jsonFile));

            JSONObject scopeObject = (JSONObject) jsonObject.get("scope");
            JSONObject userObject = (JSONObject) scopeObject.get("_user");
            cutiesDiscordName = (String) userObject.get("username");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            String message = "I haven't been able to read your discord username :((";
            JOptionPane.showMessageDialog(new JFrame(), message, "Can't resolve discord username",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
