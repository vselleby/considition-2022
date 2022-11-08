package com.selleby;

import com.selleby.models.BagType;
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
    private static final int INCREMENTATION_MAX = 32;
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
            int bestAverageDailyScore = Integer.MIN_VALUE;
            int bestOrderForDay = 0;
            int incrementStep = 0;
            int nextOrderForDay = 0;

            if (day > 0) {
                System.out.printf("New day %d orders are: %s%n", day, orders);
                System.out.println("Score: " + bestAverageDailyScore);
            }
            else {
                BagType bagType = BagType.getBagTypeFromIndex(solution.bagType);
                if (bagType.getPrice() * gameResponse.population > gameResponse.companyBudget) {
                    nextOrderForDay = (int) (gameResponse.companyBudget / bagType.getPrice());
                }
                else {
                    nextOrderForDay = gameResponse.population;
                }
            }
            for (;;) {
                orders.set(day, nextOrderForDay);
                solution.setOrders(orders);
                SubmitResponse submitResponse = api.submitGame(solution);
                int companyBudget = (int) floor(submitResponse.dailys.get(day).companyBudget);
                int averageDailyScore = futureDayOnlyScoreCalculation(day, submitResponse.dailys);
                if (averageDailyScore > bestAverageDailyScore && companyBudget >= 0) {
                    bestAverageDailyScore = averageDailyScore;
                    bestOrderForDay = nextOrderForDay;
                    bestSubmitResponse = new SubmitResponse(submitResponse);
                    orders.set(day, bestOrderForDay);
                    if (incrementStep < INCREMENTATION_MAX) {
                        incrementStep = incrementStep == 0 ? 1 : incrementStep * 2;
                    }
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

    private int defaultCustomerScoreCalculation(int startDay, List<DailyStat> dailyStats) {
        int customerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            customerScores += dailyStats.get(i).positiveCustomerScore;
            customerScores += dailyStats.get(i).negativeCustomerScore;
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        return (customerScores / iterations) - (co2 / iterations);
    }

    private int futureDayOnlyScoreCalculation(int startDay, List<DailyStat> dailyStats) {
        int dayInFuture;
        if ((startDay + forwardLookingDays) < dailyStats.size()) {
            dayInFuture = startDay + forwardLookingDays;
        }
        else {
            dayInFuture = dailyStats.size() - 1;
        }
        return (int) ((dailyStats.get(dayInFuture).positiveCustomerScore + dailyStats.get(dayInFuture).negativeCustomerScore) - dailyStats.get(dayInFuture).c02);
    }

    private int pythagorasCustomerScoreCalculation(int startDay, List<DailyStat> dailyStats) {
        int customerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            customerScores += dailyStats.get(i).positiveCustomerScore;
            customerScores += dailyStats.get(i).negativeCustomerScore;
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        if (customerScores > 0) {
            return (int) Math.sqrt(Math.pow(((double)customerScores / iterations), 2) - Math.pow(((double)co2 / iterations), 2));
        }
        return (customerScores / iterations) - (co2 / iterations);
    }

    private int negativeFirstScoreCalculation(int startDay, List<DailyStat> dailyStats) {
        int customerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            if (dailyStats.get(i).negativeCustomerScore == 0) {
                customerScores += dailyStats.get(i).positiveCustomerScore;
            }
            else {
                customerScores += dailyStats.get(i).negativeCustomerScore;
            }
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        return (customerScores / iterations) - (co2 / iterations);
    }

    private int positiveOnlyScoreCalculation(int startDay, List<DailyStat> dailyStats) {
        int customerScores = 0;
        int co2 = 0;
        int iterations = 0;
        for (int i = Math.max(0, startDay); i < (startDay + forwardLookingDays) && i < dailyStats.size(); i++) {
            customerScores += dailyStats.get(i).positiveCustomerScore;
            co2 += dailyStats.get(i).c02;
            iterations++;
        }
        if (iterations == 0) {
            return 0;
        }
        return (customerScores / iterations) - (co2 / iterations);
    }
}
