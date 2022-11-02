package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

public class Main {
    private static final String ApiKey = "0810c08c-7abc-4af9-267d-08dab8e1e0aa";
    private static final String Map = "Suburbia"; //TODO Enter what map you want to play,
     //new ones will be released on considition.com/rules
     
     // TODO: You bag type choice here. Unless changed, the bag type 1 will be selected.
    private static final Integer bagType = 1;

    public static void main(String[] args) {
        GameLayer gameLayer = new GameLayer(ApiKey);
        GameResponse gameInformation = gameLayer.mapInfo(Map, ApiKey);

        int days = (Map.equals("Suburbia") || Map.equals("Fancyville")) ? 31 : 365;
        Solver solver = new Solver(gameInformation.population, gameInformation.companyBudget, days);

        //TODO Create your own solver with SubmitResponse as return value
        Solution solution = solver.Solve(bagType);
        SubmitResponse submitResponse = gameLayer.SubmitGame(solution, Map, ApiKey);


        System.out.println("Your score is: " + submitResponse.totalScore);
        System.out.println("The game id is: " + submitResponse.gameId);
        System.out.println("The weekly results were: " + submitResponse.weeks);
        System.out.println("The amount of produced bags were: " + submitResponse.producedBags);
        System.out.println("The amount of destroyed bags were: " + submitResponse.destroyedBags);
        System.out.println(submitResponse.link);
    }
}
