package com.selleby.responses;

import com.selleby.models.DailyStat;

import java.util.List;
import java.util.Objects;

public class SubmitResponse {
    public int score;
    public String gameId;
    public int totalProducedBags;
    public int totalDestroyedBags;
    public List<DailyStat> dailys;
    public String visualizer;


    public SubmitResponse(int totalScore, String gameId, List<DailyStat> weeks, int producedBags, int destroyedBags, String visualizer){
        this.score = totalScore;
        this.gameId = gameId;
        this.dailys = weeks;
        this.totalProducedBags = producedBags;
        this.totalDestroyedBags = destroyedBags;
        this.visualizer = visualizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmitResponse that = (SubmitResponse) o;
        return score == that.score && totalProducedBags == that.totalProducedBags &&
                totalDestroyedBags == that.totalDestroyedBags && Objects.equals(gameId, that.gameId) &&
                Objects.equals(dailys, that.dailys) && Objects.equals(visualizer, that.visualizer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, gameId, totalProducedBags, totalDestroyedBags, dailys, visualizer);
    }
}


