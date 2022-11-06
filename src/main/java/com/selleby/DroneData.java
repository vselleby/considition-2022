package com.selleby;

import java.util.Arrays;

public class DroneData {
    public double[] dailyOrderFactor;
    public double mutationFactor = 0.1;

    public int additiveMutationFactor = 1;


    //todo: the other 4 factors should be clones for refund amount as well, and refund amount should also be added. a total of 10 variables.

    public DroneData(final int daysToLive) {
        dailyOrderFactor = new double[daysToLive];

        for (int i = 0; i < daysToLive; i++) {
            this.dailyOrderFactor[i] = 1.0;
        }
    }

    @Override
    public String toString() {
        return "DroneData{" +
                "dailyOrderFactor=" + Arrays.toString(dailyOrderFactor) +
                ", mutationFactor=" + mutationFactor +
                '}';
    }
}
