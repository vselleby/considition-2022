package com.selleby.responses;

import com.selleby.models.DailyStat;

import java.util.List;

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
}


