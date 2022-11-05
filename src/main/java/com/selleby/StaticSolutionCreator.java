package com.selleby;

import com.selleby.models.Solution;


public class StaticSolutionCreator extends SolutionCreator {
    public StaticSolutionCreator(Api api) {
        super(api);
    }

    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setBagType(1);
        solution.setRefundAmount(1);
        solution.setRecycleRefundChoice(false);
        solution.setBagPrice(2);
        return solution;
    }
}
