package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;


public abstract class Solver {
    protected Api api;
    protected GameResponse gameResponse;

    public Solver(Api api) {
        this.api = api;
        this.gameResponse = api.mapInfo();
    }

    public abstract SubmitResponse solve(Solution solution);
}