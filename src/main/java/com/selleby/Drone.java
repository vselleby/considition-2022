package com.selleby;


public class Drone {
    private DroneData data;

    public Drone(final int daysToLive) {
        this.data = new DroneData(daysToLive);
    }

    public void mutate(DroneData survivorDroneData, int numberOfDays) {
        this.data.mutationFactor += (Math.random() - 0.5) * 2;
        for (int i = 0; i < numberOfDays; i++) {
            this.data.dailyProductionEmissionFactor[i] = survivorDroneData.dailyProductionEmissionFactor[i] + ((Math.random() - 0.5) * 2 * this.data.mutationFactor);
            this.data.dailyTransportEmissionFactor[i] = survivorDroneData.dailyTransportEmissionFactor[i] + (Math.random() - 0.5) * 2 * this.data.mutationFactor;
            this.data.dailyWashFactor[i] = survivorDroneData.dailyWashFactor[i] + (Math.random() - 0.5) * 2 * this.data.mutationFactor;
            this.data.dailyPriceFactor[i] = survivorDroneData.dailyPriceFactor[i] + (Math.random() - 0.5) * 2 * this.data.mutationFactor;
            this.data.dailyReuseFactor[i] = survivorDroneData.dailyReuseFactor[i] + (Math.random() - 0.5) * 2 * this.data.mutationFactor;
            this.data.dailyRefundAmountFactor[i] = survivorDroneData.dailyRefundAmountFactor[i] + (Math.random() - 0.5) * 2 * this.data.mutationFactor;
        }
    }

    public DroneData getDroneData() {
        return this.data;
    }









}
