package com.selleby;

import com.selleby.models.Solution;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticSolver extends Solver {
    public StaticSolver(Api api, String mapName, int days) {
        super(api, mapName, days);
    }

    @Override
    public SubmitResponse solve(Solution solution) {
        List<Integer> orders = new ArrayList<>(Collections.nCopies(days, 20));
        solution.setOrders(orders);
        return api.submitGame(solution);
    }
}
