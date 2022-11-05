package com.selleby;

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
                Api api = new Api();
                ForwardLookingSolver solver = new ForwardLookingSolver(api, MAP_NAME, DAYS, 7);
                Solution solution = new RandomizedSolutionCreator(api, MAP_NAME).createSolution();
                Random random = new Random();

                int forwardLookingDays = random.nextInt(1, 8);
                if (runStates.containsKey(new IterationState(solution, null, forwardLookingDays))) {
                    return;
                }
                else {
                    runStates.put(new IterationState(solution, null, forwardLookingDays), 1);
                }
                System.out.printf("Running solver for: BagType: %d BagPrice: %d Refund: %d Choice: %s Forward: %d%n", solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice, forwardLookingDays);
                solver.setForwardLookingDays(forwardLookingDays);
                SubmitResponse bestResponse = solver.solve(solution);
                persistor.persist(new IterationState(solution, bestResponse, forwardLookingDays));
                if (bestResponse != null) {
                    System.out.println("Total score: " + bestResponse.score);
                    System.out.printf("BagType: %d BagPrice: %d Refund: %d Choice: %s%n", solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice);
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
