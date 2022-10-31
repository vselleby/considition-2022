import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import responses.FetchResponse;
import responses.GameResponse;
import responses.SubmitResponse;

import java.util.ArrayList;

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
         var state = Api.mapInfo(map, apiKey);

         return state;
    }

    /**
     * Submit your solution to be scored
     * @param solution list of orders, recycleRefundChoice, bagPrice and refundAmount
     * @param mapName string of chosen map
     * @param apiKey your teams api-key
     * @return The game specifics
     */
    public SubmitResponse SubmitGame(Solution solution, String mapName, String apiKey){
        var state = Api.SubmitGame(solution, mapName, apiKey);
        return state;
    }
}
