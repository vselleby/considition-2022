package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.DroneSubmitResponse;
import com.selleby.responses.SubmitResponse;

import static com.selleby.GlobalVariables.DAYS;

public class DroneSolver extends Solver<DroneSubmitResponse> {

    private Drone[] drones;

    public DroneSolver(Api api, Drone[] drones) {
        super(api);
        this.drones = drones;
    }



    @Override
    public DroneSubmitResponse solve(Solution solution) {
        SubmitResponse bestResponse = null;
        Drone bestDrone = null;

        int numberGenerations = 1000;
        for (int generationIndex = 0; generationIndex < numberGenerations; generationIndex++) {
            //run drones
            for (int i = 0; i < drones.length; i++) {
                SubmitResponse submitResponse = api.submitGame(solution);
                if (bestResponse == null || submitResponse.score > bestResponse.score ) {
                    bestDrone = drones[i];
                    bestResponse = submitResponse;
                    System.out.println("Your score is: " + bestResponse.score);
                }
            }
            DroneData bestData = bestDrone.getDroneData();
            for (int i = 0; i < 100; i++) {
                drones[i].mutate(bestData, DAYS);
            }
        }
        return new DroneSubmitResponse(bestResponse,bestDrone);
    }
}
