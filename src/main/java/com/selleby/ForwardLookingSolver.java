package com.selleby;

import com.selleby.models.DailyStat;
import com.selleby.models.Solution;
import com.selleby.responses.ForwardLookingResponse;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.selleby.GlobalVariables.DAYS;
import static java.lang.Math.floor;

public class ForwardLookingSolver extends Solver<ForwardLookingResponse> {
    private int forwardLookingDays;
    private int mapDays = DAYS;

    public ForwardLookingSolver(Api api, int forwardLookingDays) {
        super(api);
        this.forwardLookingDays = forwardLookingDays;
    }

    public void setMapDays(int mapDays) {
        this.mapDays = mapDays;
    }

    public void setForwardLookingDays(int forwardLookingDays) {
        this.forwardLookingDays = forwardLookingDays;
    }

    @Override
    public ForwardLookingResponse solve(Solution solution) {
        List<Integer> orders = new ArrayList<>(Collections.nCopies(mapDays, 0));
        SubmitResponse bestSubmitResponse = null;
        dayLoop:
        for (int day = 0; day < mapDays; day++) {
            if (day > 0) {
                System.out.printf("New day %d orders are: %s%n", day, orders);
            }
            int bestAverageDailyScore = Integer.MIN_VALUE;
            int bestOrderForDay = 0;
            int incrementStep = 0;
            int nextOrderForDay = 0;
            for (;;) {
                orders.set(day, nextOrderForDay);
                solution.setOrders(orders);
                SubmitResponse submitResponse = api.submitGame(solution);
                int companyBudget = (int) floor(submitResponse.dailys.get(day).companyBudget);
                int averageDailyScore = calculateAverageDailyScore(day, submitResponse.dailys);
                if (averageDailyScore > bestAverageDailyScore && companyBudget >= 0) {
                    bestAverageDailyScore = averageDailyScore;
                    bestOrderForDay = nextOrderForDay;
                    bestSubmitResponse = new SubmitResponse(submitResponse);
                    orders.set(day, bestOrderForDay);
                    incrementStep = incrementStep == 0 ? 1 : incrementStep * 2;
                    nextOrderForDay += incrementStep;
                }
                else if (incrementStep > 1) {
                    incrementStep = 1;
                    nextOrderForDay = bestOrderForDay + incrementStep;
                }
                else {
                    orders.set(day, bestOrderForDay);
                    continue dayLoop;
                }
            }
        }
        return new ForwardLookingResponse(bestSubmitResponse, forwardLookingDays);
    }

    private int calculateAverageDailyScore(int startDay, List<DailyStat> dailyStats) {
        int negativeCustomerScores = 0;
        int positiveCustomerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            positiveCustomerScores += dailyStats.get(i).positiveCustomerScore;
            negativeCustomerScores += dailyStats.get(i).negativeCustomerScore;
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        return (positiveCustomerScores / iterations) + (negativeCustomerScores / iterations) - (co2 / iterations);
    }
}
