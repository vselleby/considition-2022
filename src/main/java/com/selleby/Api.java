package com.selleby;

import com.google.gson.*;
import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Api {
    private static final String API_KEY = "0810c08c-7abc-4af9-267d-08dab8e1e0aa";

    private static final String BASE_PATH ="https://api.considition.com/api/game";
    private final Gson gson;

    public Api() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public GameResponse mapInfo(String mapName) {
       try {

            URL url = new URL(BASE_PATH + "/mapInfo?MapName=" + mapName);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-api-key", API_KEY);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            String response = readApiResponse(con);
            return gson.fromJson(response, GameResponse.class);

        } catch (Exception e) {
            System.out.println("Fatal error: Could not get map");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public SubmitResponse submitGame(Solution solution){
        try {
            URL url = new URL(BASE_PATH + "/submit");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("x-api-key", API_KEY);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            String solutionInJson = gson.toJson(solution);
            String response = doPost(con, solutionInJson);
            return gson.fromJson(response, SubmitResponse.class);

        } catch (Exception e) {
            System.out.println("Fatal error: Could not submit game. Retrying!");
            return submitGame(solution);
        }
    }

    private static String readApiResponse(HttpURLConnection con) throws IOException {

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return br.readLine();
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String response = br.readLine();
            throw new IOException("Http error with code " + con.getResponseCode() + " and message: " + response);
        }
    }

    private static String doPost(HttpURLConnection con, String body) throws IOException {
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("accept", "text/plain");
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        os.close();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return br.readLine();
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String response = br.readLine();
            throw new IOException("Http error with code " + con.getResponseCode() + " and message: " + response);
        }
    }
}
