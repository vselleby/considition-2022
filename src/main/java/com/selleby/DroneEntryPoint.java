package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.IterationState;
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

                Drone[] drones = new Drone[25];
                for (int i = 0; i < drones.length; i++) {
                    drones[i] = new Drone(DAYS);
                }
                DroneSolver solver = new DroneSolver(api, drones,BagType.TWO);
                Solution solution = new Solution();
                SubmitResponse bestResponse = solver.solve(solution);
                persistor.persist(new IterationState(solution,bestResponse));
            });
        } catch (IOException e) {
            System.out.println("Persistor did not start!");
            System.exit(1);
        }
    }
}
