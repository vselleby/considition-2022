package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;


public abstract class Solver<T extends SubmitResponse> {
    protected Api api;
    protected GameResponse gameResponse;

    public Solver(Api api) {
        this.api = api;
        this.gameResponse = api.mapInfo();
    }

    public abstract T solve(Solution solution);
}