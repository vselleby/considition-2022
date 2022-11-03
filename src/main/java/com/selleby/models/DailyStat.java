package com.selleby.models;

public class DailyStat {
    public int c02;
    public int customerScore;
    public int negativeCustomerScore;
    public double companyBudget;

    public DailyStat(int co2, int positiveCustomerScore, int negativeCustomerScore, double budget) {
        this.c02 = co2;
        this.customerScore = positiveCustomerScore;
        this.negativeCustomerScore = negativeCustomerScore;
        this.companyBudget = budget;
    }

    @Override
    public String toString() {
        return "DailyStat{" +
                "c02=" + c02 +
                ", customerScore=" + customerScore +
                ", negativeCustomerScore=" + negativeCustomerScore +
                ", companyBudget=" + companyBudget +
                '}';
    }
}