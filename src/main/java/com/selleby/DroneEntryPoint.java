package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;

import java.io.IOException;
import java.util.stream.IntStream;

import com.selleby.responses.SubmitResponse;

import static com.selleby.GlobalVariables.DAYS;

public class DroneEntryPoint {
    public static void main(String[] args) {
        try {
            RecordPersistor persistor = new RecordPersistor();

            IntStream.range(1, 1000).parallel().forEach(ignored -> {
                Api api = new Api();

                Drone[] drones = new Drone[100];
                for (int i = 0; i < 100; i++) {
                    drones[i] = new Drone(DAYS);
                }

                DroneSolver solver = new DroneSolver(api, drones);

                int generations = 100;
                for (int i = 0; i < generations; i++) {
                    Solution solution = new DroneSolutionCreator(api, BagType.TWO,drones[i]).createSolution();

                    System.out.printf("Running solver for: BagType: %d BagPrice: %d Refund: %d Choice: %s%n", solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice);
                    SubmitResponse bestResponse = solver.solve(solution);
                    if (bestResponse != null) {
                        System.out.println("Total score: " + bestResponse.score);
                        System.out.printf("BagType: %d BagPrice: %d Refund: %d Choice: %s%n", solution.bagType, solution.bagPrice, solution.refundAmount, solution.recycleRefundChoice);
                        System.out.println("Orders: " + solution.orders);
                        System.out.println("Visualizer: " + bestResponse.visualizer);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Persistor did not start!");
            System.exit(1);
        }
    }
}