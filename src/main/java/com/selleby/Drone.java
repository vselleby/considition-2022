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
        this.hasProducedThisRound = false; //only failures may check this.
        this.score = 0;
        this.random = new Random();
    }

    public void setDailyOrderFactor(List<Integer> startingDailyOrders) {
        for (int i =0; i < startingDailyOrders.size(); i++ ) {
            this.data.dailyOrderFactor[i] = startingDailyOrders.get(i);
        }
    }

    public void produceOffspring(DroneData survivorDroneData, boolean shouldIncreaseMutation) {
        /*for (int i = 0; i < numberOfDays; i++) {
            this.data.dailyOrderFactor[i] = Math.max(survivorDroneData.dailyOrderFactor[i] + (Math.random() - 0.5)* 2,0) * this.data.mutationFactor;
            this.data.dailyRefundAmountFactor[i] = Math.max(survivorDroneData.dailyRefundAmountFactor[i] + (Math.random() - 0.5) * 2,0) * this.data.mutationFactor;
        }*/
        this.crossData(survivorDroneData,0.5);
        this.randomlyMutateData();
        this.randomlyDeleteData();
    }
    public void cloneFromBetterDrone(DroneData survivorDroneData) {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = Math.max(survivorDroneData.dailyOrderFactor[i] + (Math.random() - 0.5)* 2,0) * this.data.mutationFactor;
        }

        //this.crossData(survivorDroneData,1);
        //this.randomlyMutateData();
        //this.randomlyDeleteData();
    }

    public void randomlyMutateData() {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = data.dailyOrderFactor[i] * random.nextDouble(1.0 - data.mutationFactor, 1.0 + data.mutationFactor);
        }
    }

    public void randomlyMutateDataByAddition() {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = data.dailyOrderFactor[i] + random.nextInt(-data.additiveMutationFactor, data.additiveMutationFactor);
        }
    }

    private void crossData(DroneData droneToCross, double crossFactor) {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = MathHelper.lerpNumber(this.data.dailyOrderFactor[i], droneToCross.dailyOrderFactor[i], crossFactor);
        }
    }
    private void randomlyDeleteData() {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = Math.random() > 0.98 ? 0 : this.data.dailyOrderFactor[i];
        }
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
