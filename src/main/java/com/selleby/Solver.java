package com.selleby;

import com.selleby.models.Solution;

import java.util.*;

public class Solver {
    private final List<Double>  bagType_price          = Arrays.asList(1.7, 1.75, 6.0, 25.0, 200.0);
    private final List<Double>  bagType_co2_transport  = Arrays.asList(3.0, 4.2, 1.8, 3.6, 12.0);
    private final List<Integer> bagType_co2_production = Arrays.asList(30, 24, 36, 42, 60);

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

    public Solution Solve(int bagType, int bagPrice, boolean recycleChoice, int refundAmount) {
        solution.setBagType(bagType);
        solution.setBagPrice(bagPrice);
        solution.setRecycleRefundChoice(recycleChoice);
        solution.setRefundAmount(refundAmount);

        List<Integer> orders = new ArrayList<>();
        for (int day = 0; day < days; day++) {
            orders.add(wasteMoney(bagType));
        }
        
        solution.setOrders(orders);
        return solution;
    }

    // Solution 1: "Spend all money day 1"
    private Integer wasteMoney(int bagtype) {
        return (int)Math.floor(companyBudget / bagType_price.get(bagtype));
    }

    // Solution 2: "Spend equally money every day"
    private Integer splitMoney(int bagtype) {
        return (int)Math.floor(companyBudget / bagType_price.get(bagtype) / days);
    }

    // Solution 3: "Everyone get one bag every day"
    private Integer holdMoney(int bagtype) {
        return (int)Math.floor(companyBudget / bagType_price.get(bagtype) / population / days);
    }
}