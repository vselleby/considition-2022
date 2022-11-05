package com.selleby.responses;

import com.selleby.Drone;

public class DroneSubmitResponse extends SubmitResponse {
    public Drone bestDrone;

    public DroneSubmitResponse(SubmitResponse response, Drone bestDrone) {
        super(response);
        this.bestDrone = bestDrone;
    }

}
