package com.selleby;

import com.selleby.models.DailyStat;
import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.selleby.Api.submitGame;
import static java.lang.Math.floor;

public class ForwardLookingSolver extends Solver {
    private int forwardLookingDays;

    public ForwardLookingSolver(GameResponse gameResponse, int days, int forwardLookingDays) {
        super(gameResponse, days);
        this.forwardLookingDays = forwardLookingDays;
    }

    public void setForwardLookingDays(int forwardLookingDays) {
        this.forwardLookingDays = forwardLookingDays;
    }

    @Override
    public SubmitResponse solve(Solution solution) {
        List<Integer> orders = new ArrayList<>(Collections.nCopies(days, 0));
        SubmitResponse bestSubmitResponse = null;
        int bestOrderForDay = 0;
        outer:
        for (int day = 0; day < days; day++) {
            if (day > 0) {
                orders.set(day - 1, bestOrderForDay);
            }
            List<DailyStat> bestDailyStats = null;
            bestOrderForDay = 0;
            int companyBudget = bestSubmitResponse == null ? gameResponse.companyBudget : (int) floor(bestSubmitResponse.dailys.get(day).companyBudget);
            for (int i = 0; i < companyBudget; i++) {
                orders.set(day, i);
                solution.setOrders(orders);
                SubmitResponse submitResponse = submitGame(solution);
                if (bestDailyStats == null || calculateAverageDailyScore(day, bestDailyStats) <
                        calculateAverageDailyScore(day, submitResponse.dailys)) {
                    System.out.println("New total score: " + submitResponse.score);
                    System.out.println("Orders: " +solution.orders);
                    bestDailyStats = submitResponse.dailys;
                    bestOrderForDay = i;
                    bestSubmitResponse = submitResponse;
                } else {
                    continue outer;
                }
            }
        }
        return bestSubmitResponse;
    }

    private int calculateAverageDailyScore(int startDay, List<DailyStat> dailyStats) {
        int customerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            customerScores += dailyStats.get(i).customerScore;
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        return (customerScores / iterations) - (co2 / iterations);
    }
}
