package com.selleby.models;

public class DailyStat {
    public double c02;
    public int positiveCustomerScore;
    public int negativeCustomerScore;
    public double companyBudget;

    public DailyStat(int co2, int positiveCustomerScore, int negativeCustomerScore, double budget) {
        this.c02 = co2;
        this.positiveCustomerScore = positiveCustomerScore;
        this.negativeCustomerScore = negativeCustomerScore;
        this.companyBudget = budget;
    }

    @Override
    public String toString() {
        return "DailyStat{" +
                "c02=" + c02 +
                ", positiveCustomerScore=" + positiveCustomerScore +
                ", negativeCustomerScore=" + negativeCustomerScore +
                ", companyBudget=" + companyBudget +
                '}';
    }
}