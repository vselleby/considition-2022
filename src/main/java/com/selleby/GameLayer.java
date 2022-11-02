package com.selleby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

public class GameLayer {
    private String apiKey;
    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    public GameLayer(String apiKey){
        this.apiKey = apiKey;
    }

    /**
     * Get information about a map.
     * @param map Specify which map to play on
     * @return The game specifics
     */
    public GameResponse mapInfo(String map, String apiKey) {
         return Api.mapInfo(map, apiKey);
    }

    /**
     * Submit your solution to be scored
     * @param solution list of orders, recycleRefundChoice, bagPrice and refundAmount
     * @param mapName string of chosen map
     * @param apiKey your teams api-key
     * @return The game specifics
     */
    public SubmitResponse SubmitGame(Solution solution, String mapName, String apiKey){
        return Api.SubmitGame(solution, mapName, apiKey);
    }
}
