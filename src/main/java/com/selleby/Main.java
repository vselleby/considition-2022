package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

public class Main {
    private static final String ApiKey = "0810c08c-7abc-4af9-267d-08dab8e1e0aa";
    private static final String Map = "Suburbia";
     //new ones will be released on considition.com/rules
     
     // TODO: You bag type choice here. Unless changed, the bag type 1 will be selected.
    private static final Integer bagType = 1;

    public static void main(String[] args) {
        GameLayer gameLayer = new GameLayer(ApiKey);
        GameResponse gameInformation = gameLayer.mapInfo(Map, ApiKey);

        int days = (Map.equals("Suburbia") || Map.equals("Fancyville")) ? 31 : 365;
        Solver solver = new Solver(gameInformation.population, gameInformation.companyBudget, days);

        SubmitResponse bestResponse = null;
        Solution bestSolution = null;
        long start = System.currentTimeMillis();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 5; j++) {
                System.out.println("i is: " + i + " j is: " + j + " time is: " + (System.currentTimeMillis() - start));
                Solution solution = solver.Solve(bagType, i, true, j);
                SubmitResponse submitResponse = gameLayer.SubmitGame(solution, Map, ApiKey);
                if (bestResponse == null || submitResponse.score > bestResponse.score) {
                    bestResponse = submitResponse;
                    bestSolution = solution;
                    System.out.println("new best solution!");
                    System.out.println("Bag price is: " + i);
                    System.out.println("Refund price is: " + j);
                    System.out.println("Your score is: " + bestResponse.score);
                    System.out.println("The game id is: " + bestResponse.gameId);
                    System.out.println("The weekly results were: " + bestResponse.dailys);
                    System.out.println("The amount of produced bags were: " + bestResponse.totalProducedBags);
                    System.out.println("The amount of destroyed bags were: " + bestResponse.totalDestroyedBags);
                    System.out.println(bestResponse.visualizer);
                }
            }
        }

        System.out.println("Best solution: BP:" + bestSolution.bagPrice + " RP: " + bestSolution.refundAmount + " orders: " + bestSolution.orders);

        System.out.println("Your score is: " + bestResponse.score);
        System.out.println("The game id is: " + bestResponse.gameId);
        System.out.println("The weekly results were: " + bestResponse.dailys);
        System.out.println("The amount of produced bags were: " + bestResponse.totalProducedBags);
        System.out.println("The amount of destroyed bags were: " + bestResponse.totalDestroyedBags);
        System.out.println(bestResponse.visualizer);
    }
}
