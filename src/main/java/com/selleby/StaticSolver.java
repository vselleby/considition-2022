package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.selleby.GlobalVariables.DAYS;

public class StaticSolver extends Solver {
    public StaticSolver(Api api) {
        super(api);
    }

    @Override
    public SubmitResponse solve(Solution solution) {
        List<Integer> orders = new ArrayList<>(Collections.nCopies(DAYS, 20));
        solution.setOrders(orders);
        return api.submitGame(solution);
    }
}
