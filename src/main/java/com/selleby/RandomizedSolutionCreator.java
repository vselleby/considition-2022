package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;

import java.util.Random;

public class RandomizedSolutionCreator extends SolutionCreator {
    public RandomizedSolutionCreator(Api api, String mapName) {
        super(api, mapName);
    }

    @Override
    public Solution createSolution() {
        Random random = new Random();
        //int randomBagPick = random.nextInt(4);
        //BagType bagType = BagType.values()[randomBagPick];
        BagType bagType = BagType.TWO;
        Solution solution = new Solution();
        solution.setMapName(mapName);
        solution.setBagType(bagType.getIndex());
        solution.setBagPrice((int) Math.ceil(random.nextDouble(0.5, 1.5) * bagType.getPrice()));
        solution.setRefundAmount((int) Math.floor(random.nextDouble(0.5, 1) * bagType.getPrice()));
        solution.setRecycleRefundChoice(random.nextBoolean());
        return solution;
    }
}
