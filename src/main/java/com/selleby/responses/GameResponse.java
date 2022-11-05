package com.selleby.responses;

public class GameResponse {
    public int population;
    public int companyBudget;
    public String behavior;

    public GameResponse(int population, int companyBudget, String behavior) {
        this.population = population;
        this.companyBudget = companyBudget;
        this.behavior = behavior;
    }
}
