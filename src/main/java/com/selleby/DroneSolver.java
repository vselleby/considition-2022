package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;
import com.selleby.responses.DroneSubmitResponse;
import com.selleby.responses.SubmitResponse;

import java.util.*;

import static com.selleby.GlobalVariables.DAYS;
import static com.selleby.GlobalVariables.MAP_NAME;

public class DroneSolver extends Solver<DroneSubmitResponse> {
    private final List<Drone> drones;
    private final List<Integer> initialOrders;

    public DroneSolver(Api api, List<Drone> drones) {
        super(api);
        this.drones = drones;
        initialOrders = new ArrayList<>(List.of(8, 3, 7, 0, 0, 0, 0, 8, 8, 15, 3, 0, 0, 0, 0, 0, 0, 4, 10, 2, 8, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0));
    }

    private Integer defaultOrdering(DroneData data, int day) {
        return (int) (Math.abs(data.dailyOrderFactor.get(day)));
    }

    private void resetDrones() {
        for (Drone drone : drones) {
            drone.setDailyOrderFactor(initialOrders);
        }
    }

    @Override
    public DroneSubmitResponse solve(Solution solution) {
        SubmitResponse bestResponse = null;

        Drone bestDrone = null;

        solution.setBagType(BagType.TWO.getIndex());
        solution.setMapName(MAP_NAME);
        solution.setRecycleRefundChoice(true);
        solution.setRefundAmount(2);
        solution.setBagPrice(1);

        solution.setOrders(initialOrders);
        resetDrones();

        SubmitResponse initialResponse = api.submitGame(solution);
        int numberGenerations = 100;
        for (int generationIndex = 0; generationIndex < numberGenerations; generationIndex++) {
            //run drones
            for (Drone drone : drones) {
                //Do calculations for one drone.
                drone.survivedRound = false;
                drone.randomlyMutateData();
                drone.randomlyDeleteData();
                List<Integer> orders = new ArrayList<>();
                for (int day = 0; day < DAYS; day++) {
                    orders.add(defaultOrdering(drone.getDroneData(), day));
                }
                solution.setOrders(orders);
                System.out.println(orders);

                SubmitResponse submitResponse = api.submitGame(solution);
                System.out.println("Score: " + submitResponse.score);
                drone.score = submitResponse.score;
                if (submitResponse.score >= initialResponse.score && (bestResponse == null || submitResponse.score > bestResponse.score)) { //determine best drone out of current batch.
                    bestDrone = drone;
                    bestResponse = submitResponse;
                    bestDrone.survivedRound = true;
                    System.out.println("New best score: " + submitResponse.score);
                    System.out.println("Orders: " + orders);
                    resetMutationFactor();
                }
            }


            drones.sort(Comparator.comparingDouble(drone -> drone.score));

            //reproduce using best drone.
            if (bestDrone != null && bestDrone.survivedRound) {
                for (Drone drone : drones) {
                    drone.cloneFromBetterDrone(bestDrone.getDroneData());
                }
            }
            else {
                resetDrones();
                increaseMutiationFactor();
            }
        }
        return new DroneSubmitResponse(bestResponse == null ? initialResponse : bestResponse, bestDrone);
    }

    private void resetMutationFactor() {
        for (Drone drone : drones) {
            drone.getDroneData().mutationFactor = 0.01;
        }
    }

    private void increaseMutiationFactor() {
        for (Drone drone : drones) {
            drone.getDroneData().mutationFactor += 0.01;

        }
    }
}
