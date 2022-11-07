package com.selleby;


import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Random;

public class Drone {
    private final DroneData data;
    public boolean survivedRound;
    public boolean hasProducedThisRound;
    @Expose(serialize = false)
    private final transient Random random;
    public int score;

    public Drone(final int daysToLive) {
        this.data = new DroneData(daysToLive);
        this.survivedRound = false;
        this.hasProducedThisRound = false;
        this.score = 0;
        this.random = new Random();
    }

    public void setDailyOrderFactor(List<Integer> startingDailyOrders) {
        for (int i =0; i < startingDailyOrders.size(); i++ ) {
            this.data.dailyOrderFactor.set(i, Double.valueOf(startingDailyOrders.get(i)));
        }
    }


    public void cloneFromBetterDrone(DroneData survivorDroneData) {
        for (int i = 0; i < data.dailyOrderFactor.size(); i++) {
            data.dailyOrderFactor.set(i, survivorDroneData.dailyOrderFactor.get(i));
        }
    }

    public void randomlyMutateData() {
        for (int i = 0; i < this.data.dailyOrderFactor.size(); i++) {
            if (data.dailyOrderFactor.get(i) == 0) {
                data.dailyOrderFactor.set(i, random.nextDouble(0, 1.0 + data.mutationFactor));
            }
            else {
                this.data.dailyOrderFactor.set(i, data.dailyOrderFactor.get(i) * random.nextDouble(1.0 - data.mutationFactor, 1.0 + data.mutationFactor));
            }
        }
    }

    public void randomlyDeleteData() {
        this.data.dailyOrderFactor.replaceAll(originalValue -> random.nextDouble() > 0.99 ? 0 : originalValue);
    }

    public DroneData getDroneData() {
        return this.data;
    }
    @Override
    public String toString() {
        return "Drone{" +
                "data=" + data +
                ", survivedRound=" + survivedRound +
                ", hasProducedThisRound=" + hasProducedThisRound +
                ", score=" + score +
                '}';
    }
}
