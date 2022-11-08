package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;

import java.util.Random;

import static com.selleby.GlobalVariables.MAP_NAME;

public class RandomizedSolutionCreator extends SolutionCreator {
    public RandomizedSolutionCreator(Api api) {
        super(api);
    }

    @Override
    public Solution createSolution() {
        Random random = new Random();
        //int randomBagPick = random.nextInt(4);
        //BagType bagType = BagType.values()[randomBagPick];
        BagType bagType = BagType.TWO;
        Solution solution = new Solution();
        solution.setMapName(MAP_NAME);
        solution.setBagType(bagType.getIndex());
        solution.setBagPrice(random.nextInt((int) Math.ceil(bagType.getPrice()), (int) Math.ceil(bagType.getPrice()) * 2));
        solution.setRefundAmount(random.nextInt(0, (int) (bagType.getPrice() * 3)));
        solution.setRecycleRefundChoice(random.nextBoolean());
        return solution;
    }
}
