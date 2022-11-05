package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;


public abstract class Solver {
    protected Api api;
    protected GameResponse gameResponse;
    protected int days;

    public Solver(Api api, String mapName, int days) {
        this.api = api;
        this.gameResponse = api.mapInfo(mapName);
        this.days = days;
    }

    public abstract SubmitResponse solve(Solution solution);
}