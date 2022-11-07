package com.selleby;

import com.selleby.models.IterationState;
import com.selleby.models.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.selleby.responses.SubmitResponse;

import static com.selleby.GlobalVariables.DAYS;

public class DroneEntryPoint {
    private static final int NUMBER_OF_DRONES = 5;
    public static void main(String[] args) {
        try {
            RecordPersistor persistor = new RecordPersistor();

            IntStream.range(1, 1000).parallel().forEach(ignored -> {
                Api api = new Api();

                List<Drone> drones = new ArrayList<>();
                for (int i = 0; i < NUMBER_OF_DRONES; i++) {
                    drones.add(new Drone(DAYS));
                }
                DroneSolver solver = new DroneSolver(api, drones);
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
