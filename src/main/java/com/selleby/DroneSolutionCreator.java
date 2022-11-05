package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;

import java.util.ArrayList;
import java.util.List;

import static com.selleby.GlobalVariables.DAYS;
import static com.selleby.GlobalVariables.MAP_NAME;

public class DroneSolutionCreator extends SolutionCreator {


    private BagType bagType;
    private Drone drone;


    public DroneSolutionCreator(Api api, BagType bagType, Drone drone) {
        super(api);
        this.bagType = bagType;
        this.drone = drone;
    }


    private Integer defaultOrdering(DroneData data, int day, BagType bagType) {
        return (int) (Math.ceil(1 * api.mapInfo().population * bagType.getPrice()
                * Math.abs( data.dailyPriceFactor[day] * data.dailyProductionEmissionFactor[day] * data.dailyReuseFactor[day]
                * data.dailyWashFactor[day] * data.dailyTransportEmissionFactor[day]) ) ) ;
    }


    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setBagType(this.bagType.getIndex() );
        solution.setMapName(MAP_NAME);
        solution.setRecycleRefundChoice(false);
        solution.setRefundAmount( (int)(bagType.getPrice() * 0.8) );
        solution.setBagPrice((int) bagType.getPrice() );

        List<Integer> orders = new ArrayList<>();
        for (int day = 0; day < DAYS; day++) {
            orders.add(defaultOrdering(drone.getDroneData(), day, bagType));
        }

        solution.setOrders(orders);

        return solution;
    }
}
