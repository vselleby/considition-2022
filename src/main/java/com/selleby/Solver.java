package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;


public abstract class Solver {
    protected GameResponse gameResponse;
    protected int days;

    public Solver(GameResponse gameResponse, int days) {
        this.gameResponse = gameResponse;
        this.days = days;
    }

    public abstract SubmitResponse solve(Solution solution);
}