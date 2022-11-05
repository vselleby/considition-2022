package com.selleby;

import com.selleby.models.Solution;


public class StaticSolutionCreator extends SolutionCreator {
    public StaticSolutionCreator(Api api, String mapName) {
        super(api, mapName);
    }

    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setBagType(2);
        solution.setRefundAmount(5);
        solution.setMapName(mapName);
        solution.setRecycleRefundChoice(false);
        solution.setBagPrice(2);
        return solution;
    }
}
