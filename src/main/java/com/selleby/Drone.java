package com.selleby;


import java.util.Comparator;

/*class DroneComparator implements Comparator<Drone> {

    // Overriding compare()method of Comparator
    public int compare(Drone o1, Drone o2) {
        return (o1.score - o2.score);
    }
}*/

public class Drone {
    private DroneData data;

    public boolean survivedRound;
    public boolean hasProducedThisRound;

    public int score;

    public Drone(final int daysToLive) {
        this.data = new DroneData(daysToLive);
        this.survivedRound = false;
        this.hasProducedThisRound = false; //only failures may check this.
        this.score = 0;
    }

    public void setDailyOrderFactor(int[] startingDailyOrders) {
        for (int i =0; i < startingDailyOrders.length; i++ ) {
            this.data.dailyOrderFactor[i] = startingDailyOrders[i];
        }
    }

    public void produceOffspring(DroneData survivorDroneData, boolean shouldIncreaseMutation) {
        this.data.mutationFactor = shouldIncreaseMutation ? Math.max( this.data.mutationFactor + (Math.random()),0) : 1;
        /*for (int i = 0; i < numberOfDays; i++) {
            this.data.dailyOrderFactor[i] = Math.max(survivorDroneData.dailyOrderFactor[i] + (Math.random() - 0.5)* 2,0) * this.data.mutationFactor;
            this.data.dailyRefundAmountFactor[i] = Math.max(survivorDroneData.dailyRefundAmountFactor[i] + (Math.random() - 0.5) * 2,0) * this.data.mutationFactor;
        }*/
        this.crossData(survivorDroneData,0.5);
        this.randomlyMutateData();
        this.randomlyDeleteData();
    }
    public void cloneFromBetterDrone(DroneData survivorDroneData, boolean shouldIncreaseMutation) {
        this.data.mutationFactor = shouldIncreaseMutation ? Math.max( this.data.mutationFactor + (Math.random()),0) : 1;
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = Math.max(survivorDroneData.dailyOrderFactor[i] + (Math.random() - 0.5)* 2,0) * this.data.mutationFactor;
            this.data.dailyRefundAmountFactor[i] = Math.max(survivorDroneData.dailyRefundAmountFactor[i] + (Math.random() - 0.5) * 2,0) * this.data.mutationFactor;
        }

        //this.crossData(survivorDroneData,1);
        //this.randomlyMutateData();
        //this.randomlyDeleteData();
    }

    private void randomlyMutateData() {
        for (int i = 0; i < this.data.dailyOrderFactor.length; i++) {
            this.data.dailyOrderFactor[i] = Math.max(this.data.dailyOrderFactor[i] + (Math.random() - 0.5)* 2,0) * this.data.mutationFactor;
            this.data.dailyRefundAmountFactor[i] = Math.max(this.data.dailyRefundAmountFactor[i] + (Math.random() - 0.5) * 2,0) * this.data.mutationFactor;
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









}
