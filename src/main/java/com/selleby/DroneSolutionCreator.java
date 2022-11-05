package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;

import java.util.ArrayList;
import java.util.List;

public class DroneSolutionCreator extends SolutionCreator {


    private BagType bagType;
    private Drone drone;


    public DroneSolutionCreator(Api api, String mapName, BagType bagType, Drone drone) {
        super(api, mapName);
        this.bagType = bagType;
        this.drone = drone;
    }


    private Integer defaultOrdering(DroneData data, int day, BagType bagType) {
        return (int) (Math.ceil(1 * api.mapInfo(mapName).population * bagType.getPrice()
                * Math.abs( data.dailyPriceFactor[day] * data.dailyProductionEmissionFactor[day] * data.dailyReuseFactor[day]
                * data.dailyWashFactor[day] * data.dailyTransportEmissionFactor[day]) ) ) ;
    }


    @Override
    public Solution createSolution() {
        Solution solution = new Solution();
        solution.setBagType(this.bagType.getIndex() );
        solution.setMapName(mapName);
        solution.setRecycleRefundChoice(false);
        solution.setRefundAmount( (int)(bagType.getPrice() * 0.8) );
        solution.setBagPrice((int) bagType.getPrice() );

        List<Integer> orders = new ArrayList<>();
        for (int day = 0; day < 31; day++) { //TODO: DAYS.
            orders.add(defaultOrdering(drone.getDroneData(), day, bagType));
        }

        solution.setOrders(orders);

        return solution;
    }
}
