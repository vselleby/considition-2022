package com.selleby;

import com.selleby.models.Solution;

import static com.selleby.GlobalVariables.MAP_NAME;


public class StaticSolutionCreator extends SolutionCreator {
    public StaticSolutionCreator(Api api) {
        super(api);
    }

    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setMapName(MAP_NAME);
        solution.setBagType(2);
        solution.setRefundAmount(1);
        solution.setRecycleRefundChoice(true);
        solution.setBagPrice(2);
        return solution;
    }
}
