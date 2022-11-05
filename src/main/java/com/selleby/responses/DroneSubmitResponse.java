package com.selleby.responses;

import com.selleby.Drone;

public class DroneSubmitResponse extends SubmitResponse {
    public Drone bestDrone;

    public DroneSubmitResponse(SubmitResponse response, Drone bestDrone) {
        super(response.score, response.gameId, response.dailys, response.totalProducedBags, response.totalDestroyedBags, response.visualizer);
        this.bestDrone = bestDrone;
    }

}
