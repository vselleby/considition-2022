package com.selleby;

public class DroneData {


    public double[] dailyProductionEmissionFactor;
    public double[] dailyTransportEmissionFactor;
    public double[] dailyWashFactor;
    public double[] dailyPriceFactor;
    public double[] dailyReuseFactor;

    //todo: the other 4 factors should be clones for refund amount as well, and refund amount should also be added. a total of 10 variables.

    public double[] dailyRefundAmountFactor;

    public double mutationFactor;

    public DroneData(final int daysToLive) {
        dailyProductionEmissionFactor = new double[daysToLive];
        dailyTransportEmissionFactor = new double[daysToLive];
        dailyWashFactor = new double[daysToLive];
        dailyPriceFactor = new double[daysToLive];
        dailyReuseFactor = new double[daysToLive];
        dailyRefundAmountFactor = new double[daysToLive];
        this.mutationFactor = 1.0;

        for (int i = 0; i < daysToLive; i++) {
            this.dailyProductionEmissionFactor[i] = 1.0;
            this.dailyTransportEmissionFactor[i] = 1.0;
            this.dailyWashFactor[i] = 1.0;
            this.dailyPriceFactor[i] = 1.0;
            this.dailyReuseFactor[i] = 1.0;
            this.dailyRefundAmountFactor[i] = 1.0;
        }

    }

}
