package com.selleby;

import com.selleby.models.Solution;

import java.util.*;

public class Solver {

    enum BagType {
        ONE(1, 1.7, 0, 1, 3, 30),
        TWO(2, 1.75, 1, 2, 4.2, 24),
        THREE(3, 6.0, 5, 3, 1.8, 36),
        FOUR(4, 25.0, 9, 5, 3.6, 42),
        FIVE(5, 200.0, 12, 7, 12, 60);

        private final int index;
        private final double price;
        private final int reuses;
        private final int washTimeInDays;
        private final double transportEmissions;
        private final int productionEmissions;

        BagType(int index, double price, int reuses, int washTimeInDays, double transportEmissions, int productionEmissions) {
            this.index = index;
            this.price = price;
            this.reuses = reuses;
            this.washTimeInDays = washTimeInDays;
            this.transportEmissions = transportEmissions;
            this.productionEmissions = productionEmissions;
        }

        public int getIndex() {
            return index;
        }

        public double getPrice() {
            return price;
        }

        public int getReuses() {
            return reuses;
        }

        public int getWashTimeInDays() {
            return washTimeInDays;
        }

        public double getTransportEmissions() {
            return transportEmissions;
        }

        public int getProductionEmissions() {
            return productionEmissions;
        }
    }

    public int population;
    public int companyBudget;
    public int days;

    private final Solution solution;

    public Solver(int population, int companyBudget, int days) {
        this.population = population;
        this.companyBudget = companyBudget;
        this.days = days;

        solution = new Solution();
    }

    public Solution Solve(BagType bagType, int bagPrice, boolean recycleChoice, int refundAmount) {
        solution.setBagType(bagType.getIndex());
        solution.setBagPrice(bagPrice);
        solution.setRecycleRefundChoice(recycleChoice);
        solution.setRefundAmount(refundAmount);

        List<Integer> orders = new ArrayList<>();
        for (int day = 0; day < days; day++) {
            orders.add(someWhatDecentSolver(day, bagType));
        }

        solution.setOrders(orders);
        return solution;
    }

    private Integer someWhatDecentSolver(int day, BagType bagType) {
        if (day % 7 == 0) {
            return (int) Math.ceil(2 * population * bagType.getPrice());
        }
        return 0;
    }
}