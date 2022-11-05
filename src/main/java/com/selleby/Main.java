package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.IterationState;
import com.selleby.models.Solution;
import com.selleby.responses.SubmitResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;


public class Main {
    private static final String MAP_NAME = "Fancyville";
    private static final int DAYS = 31;

    public static void main(String[] args) {
        try {
            RecordPersistor persistor = new RecordPersistor();

            Map<IterationState, Integer> runStates = new ConcurrentHashMap<>();
            IntStream.range(1, 1000).parallel().forEach(ignored -> {
                ForwardLookingSolver solver = new ForwardLookingSolver(new Api(), MAP_NAME, DAYS, 7);
                Random random = new Random();
                //int randomBagPick = random.nextInt(4);
                //BagType bagType = BagType.values()[randomBagPick];
                BagType bagType = BagType.TWO;
                Solution solution = new Solution();
                solution.setMapName(MAP_NAME);
                solution.setBagType(bagType.getIndex());
                solution.setBagPrice((int) Math.ceil(random.nextDouble(0.5, 1.5) * bagType.getPrice()));
                solution.setRefundAmount((int) Math.floor(random.nextDouble(0.5, 1) * bagType.getPrice()));
                solution.setRecycleRefundChoice(random.nextBoolean());
                int forwardLookingDays = random.nextInt(1, 8);
                if (runStates.containsKey(new IterationState(solution, null, forwardLookingDays))) {
                    return;
                }
                else {
                    runStates.put(new IterationState(solution, null, forwardLookingDays), 1);
                }
                System.out.printf("Running solver for: BagType: %d BagPrice: %d Refund: %d Choice: %s Forward: %d%n", bagType.getIndex(), solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice, forwardLookingDays);
                solver.setForwardLookingDays(forwardLookingDays);
                SubmitResponse bestResponse = solver.solve(solution);
                persistor.persist(new IterationState(solution, bestResponse, forwardLookingDays));
                if (bestResponse != null) {
                    System.out.println("Total score: " + bestResponse.score);
                    System.out.printf("BagType: %d BagPrice: %d Refund: %d Choice: %s%n", bagType.getIndex(), solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice);
                    System.out.println("Orders: " + solution.orders);
                    System.out.println("Visualizer: " + bestResponse.visualizer);
                }
            });
        } catch (IOException e) {
            System.out.println("Persistor did not start!");
            System.exit(1);
        }
    }
}
