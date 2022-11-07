package com.selleby;

import java.util.ArrayList;
import java.util.List;

public class DroneData {
    public List<Double> dailyOrderFactor;
    public double mutationFactor = 0.01;

    public DroneData(final int daysToLive) {
        dailyOrderFactor = new ArrayList<>();
        for (int i = 0; i < daysToLive; i++) {
            dailyOrderFactor.add(1.0);
        }
    }

    @Override
    public String toString() {
        return "DroneData{" +
                "dailyOrderFactor=" + dailyOrderFactor +
                ", mutationFactor=" + mutationFactor +
                '}';
    }
}
