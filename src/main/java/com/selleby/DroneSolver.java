package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;
import com.selleby.responses.DroneSubmitResponse;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DroneSolver extends Solver {

    private Drone[] drones;

    public DroneSolver(Api api, String mapName, int days, Drone[] drones) {
        super(api,mapName,days);
        this.drones = drones;
    }



    @Override
    public SubmitResponse solve(Solution solution) {
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
                drones[i].mutate(bestData,days);
            }
        }
        return new DroneSubmitResponse(bestResponse,bestDrone);
    }
}
