package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;
import com.selleby.responses.DroneSubmitResponse;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.selleby.GlobalVariables.DAYS;
import static com.selleby.GlobalVariables.MAP_NAME;

public class DroneSolver extends Solver<DroneSubmitResponse> {
    private final List<Drone> drones;
    private final BagType bagType;
    private final List<Integer> initialOrders;

    public DroneSolver(Api api, List<Drone> drones, BagType bagType) {
        super(api);
        this.drones = drones;
        this.bagType = bagType;
        initialOrders = new ArrayList<>(List.of(46, 1, 7, 0, 3, 1, 0, 7, 7, 3, 7, 7, 7, 0, 3, 0, 7, 1, 3, 7, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    }

    private Integer defaultOrdering(DroneData data, int day, BagType bagType) {
        return (int) (1*Math.abs(data.dailyOrderFactor[day]));
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

        solution.setBagType(this.bagType.getIndex() );
        solution.setMapName(MAP_NAME);
        solution.setRecycleRefundChoice(true);
        solution.setRefundAmount(1);
        solution.setBagPrice(1);

        solution.setOrders(initialOrders);
        resetDrones();

        SubmitResponse initialResponse = api.submitGame(solution);
        int numberGenerations = 10;
        for (int generationIndex = 0; generationIndex < numberGenerations; generationIndex++) {
            //run drones
            for (Drone drone : drones) {
                //Do calculations for one drone.
                drone.survivedRound = false;
                drone.randomlyMutateData();
                List<Integer> orders = new ArrayList<>();
                for (int day = 0; day < DAYS; day++) {
                    orders.add(defaultOrdering(drone.getDroneData(), day, bagType));
                }
                solution.setOrders(orders);

                System.out.printf("%s%n", orders);
                SubmitResponse submitResponse = api.submitGame(solution);
                drone.score = submitResponse.score;
                if (submitResponse.score > initialResponse.score && (bestResponse == null || submitResponse.score > bestResponse.score)) { //determine best drone out of current batch.
                    bestDrone = drone;
                    bestResponse = submitResponse;
                    bestDrone.survivedRound = true;
                    System.out.println("New best score: " + submitResponse.score);
                    System.out.println("Orders: " + orders);
                    resetMutationFactor();
                }
            }


            drones.sort(Comparator.comparingDouble(drone -> drone.score));
            System.out.printf("Drone results: %s%n", drones);

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
            drone.getDroneData().mutationFactor = 0.1;
            drone.getDroneData().additiveMutationFactor = 1;
        }
    }

    private void increaseMutiationFactor() {
        for (Drone drone : drones) {
            drone.getDroneData().mutationFactor += 0.1;
            drone.getDroneData().additiveMutationFactor += 1;

        }
    }
}
