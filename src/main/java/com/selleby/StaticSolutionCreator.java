package com.selleby;

import com.selleby.models.Solution;


public class StaticSolutionCreator extends SolutionCreator {
    public StaticSolutionCreator(Api api, String mapName) {
        super(api, mapName);
    }

    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setBagType(5);
        solution.setRefundAmount(200);
        solution.setMapName(mapName);
        solution.setRecycleRefundChoice(false);
        solution.setBagPrice(200);
        return solution;
    }
}
