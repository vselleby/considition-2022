package com.selleby;

import com.selleby.models.Solution;

public abstract class SolutionCreator {
    protected final Api api;

    public SolutionCreator(Api api) {
        this.api = api;
    }


    public abstract Solution createSolution();
}
