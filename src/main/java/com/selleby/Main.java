package com.selleby;

import com.selleby.models.IterationState;
import com.selleby.models.Solution;
import com.selleby.responses.ForwardLookingResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) {
        try {
            RecordPersistor persistor = new RecordPersistor();

            Map<Solution, ForwardLookingResponse> solutionResponsePairs = new ConcurrentHashMap<>();
            Map<IterationState, Integer> runStates = new ConcurrentHashMap<>();
            IntStream.range(1, 5000).parallel().forEach(ignored -> {
                Api api = new Api();
                Solution solution = new RandomizedSolutionCreator(api).createSolution();
                Random random = new Random();

                int[] forwardLookingValues = new int[] {3, 6, 7, 8, 13, 14, 15};
                int forwardLookingDays = forwardLookingValues[random.nextInt(0, 7)];
                ForwardLookingSolver solver = new ForwardLookingSolver(api, forwardLookingDays);
                /*
                if (runStates.containsKey(new IterationState(solution, null))) {
                    return;
                }
                else {
                    runStates.put(new IterationState(solution.copyBaseInformation(), null), 1);
                }*/
                System.out.printf("Running solver for: BagType: %d BagPrice: %d Refund: %d Choice: %s Forward: %d%n",
                        solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice, forwardLookingDays);
                solver.setForwardLookingDays(forwardLookingDays);
                ForwardLookingResponse bestResponse = solver.solve(solution);
                solutionResponsePairs.putIfAbsent(solution, bestResponse);
                persistor.persist(new IterationState(solution, bestResponse));
                if (bestResponse != null) {
                    System.out.println("Total score: " + bestResponse.score);
                    System.out.printf("BagType: %d BagPrice: %d Refund: %d Choice: %s forward looking: %d%n",
                            solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice, forwardLookingDays);
                    System.out.println("Orders: " + solution.orders);
                    System.out.println("Visualizer: " + bestResponse.visualizer);
                }
            });
            var sortedEntries = new ArrayList<>(solutionResponsePairs.entrySet());
            sortedEntries.sort(Comparator.comparingInt(o -> o.getValue().score));

            sortedEntries.forEach(entry -> {
                ForwardLookingResponse response = entry.getValue();
                Solution input = entry.getKey();
                System.out.println("Score: " + response.score);
                System.out.printf("BagType: %d BagPrice: %d Refund: %d Choice: %s forward looking: %d%n", input.bagType,
                        input.bagPrice, input.refundAmount, input.recycleRefundChoice, response.forwardLookingDays);
                System.out.println("Orders: " + input.orders);
                System.out.println("---------------------------------");

            });
        } catch (IOException e) {
            System.out.println("Persistor did not start!");
            System.exit(1);
        }
    }
}
