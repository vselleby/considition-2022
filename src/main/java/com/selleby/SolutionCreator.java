package com.selleby;

import com.selleby.models.Solution;

public abstract class SolutionCreator {
    protected final Api api;
    protected final String mapName;

    public SolutionCreator(Api api, String mapName) {
        this.api = api;
        this.mapName = mapName;
    }


    public abstract Solution createSolution();
}
