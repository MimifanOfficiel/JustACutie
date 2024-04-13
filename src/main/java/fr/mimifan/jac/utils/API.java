package fr.mimifan.jac.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class API {

    private static final API instance = new API();


    public String getResponse(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { content.append(inputLine); }
            in.close();
            return content.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static API getInstance() {
        return instance;
    }
}
