package models;

public class WeeklyStat {
    public int week;
    public int co2;
    public int positiveCustomerScore;
    public int negativeCustomerScore;
    public double budget;

    public WeeklyStat(int week, int co2, int positiveCustomerScore, int negativeCustomerScore, double budget) {
        this.week = week;
        this.co2 = co2;
        this.positiveCustomerScore = positiveCustomerScore;
        this.negativeCustomerScore = negativeCustomerScore;
        this.budget = budget;
    }
}