package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

import java.util.Arrays;

public class Main {
    private static final String ApiKey = "0810c08c-7abc-4af9-267d-08dab8e1e0aa";
    private static final String Map = "Suburbia";
    //new ones will be released on considition.com/rules

    // TODO: You bag type choice here. Unless changed, the bag type 1 will be selected.

    public static void main(String[] args) {
        GameLayer gameLayer = new GameLayer(ApiKey);
        GameResponse gameInformation = gameLayer.mapInfo(Map, ApiKey);

        int days = (Map.equals("Suburbia") || Map.equals("Fancyville")) ? 31 : 365;
        Solver solver = new Solver(gameInformation.population, gameInformation.companyBudget, days);

        final SubmitResponse[] bestResponse = {null};
        final Solver.BagType[] bestBagType = {null};
        long start = System.currentTimeMillis();
        Arrays.stream(Solver.BagType.values()).parallel().forEach(
                bagType -> {
                    int bagProductionPrice = (int) bagType.getPrice();
                    for (int bagPrice = Math.max(1, bagProductionPrice - 2); bagPrice <= bagProductionPrice + 5; bagPrice++) {
                        for (int refundAmount = Math.max(1, bagProductionPrice - 10); refundAmount <= bagProductionPrice + 1; refundAmount++) {
                            System.out.println("bagPrice is: " + bagPrice + " refundAmount is: " + refundAmount + " time is: " + (System.currentTimeMillis() - start));
                            Solution solution = solver.Solve(bagType, bagPrice, true, refundAmount);
                            SubmitResponse submitResponse = gameLayer.SubmitGame(solution, Map, ApiKey);
                            if (bestResponse[0] == null || submitResponse.score > bestResponse[0].score) {
                                bestResponse[0] = submitResponse;
                                bestBagType[0] = bagType;
                                System.out.println("new best solution for BagType" + bagType);
                                System.out.println("Bag price is: " + bagPrice);
                                System.out.println("Refund price is: " + refundAmount);
                                System.out.println("Orders are: " + solution.orders);
                                System.out.println("Your score is: " + bestResponse[0].score);
                                System.out.println("The game id is: " + bestResponse[0].gameId);
                                System.out.println("The weekly results were: " + bestResponse[0].dailys);
                                System.out.println("The amount of produced bags were: " + bestResponse[0].totalProducedBags);
                                System.out.println("The amount of destroyed bags were: " + bestResponse[0].totalDestroyedBags);
                                System.out.println(bestResponse[0].visualizer);
                            }
                        }
                    }
                }
        );


        System.out.println("Best for bagType: " + bestBagType[0]);
        assert bestResponse[0] != null;
        System.out.println("Your score is: " + bestResponse[0].score);
        System.out.println("The game id is: " + bestResponse[0].gameId);
        System.out.println("The weekly results were: " + bestResponse[0].dailys);
        System.out.println("The amount of produced bags were: " + bestResponse[0].totalProducedBags);
        System.out.println("The amount of destroyed bags were: " + bestResponse[0].totalDestroyedBags);
        System.out.println(bestResponse[0].visualizer);
    }
}
