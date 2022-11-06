package com.selleby;

import com.selleby.models.BagType;
import com.selleby.models.Solution;
import com.selleby.responses.DroneSubmitResponse;
import com.selleby.responses.SubmitResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.selleby.GlobalVariables.DAYS;
import static com.selleby.GlobalVariables.MAP_NAME;

public class DroneSolver extends Solver<DroneSubmitResponse> {

    private Drone[] drones;
    private BagType bagType;

    private int[] initialOrders;

    public DroneSolver(Api api, Drone[] drones, BagType bagType) {
        super(api);
        this.drones = drones;
        this.bagType = bagType;
        initialOrders = new int[]{235,0,0,0,0,0,315,7,68,0,0,30,0,68,50,14,48,40,0,0,38,0,42,44,0,0,0,0,0,0,1};
        for (int i = 0; i < drones.length; i++) {

            this.drones[i].setDailyOrderFactor(initialOrders);
        }
    }

    private Integer defaultOrdering(DroneData data, int day, BagType bagType) {
        return (int) (1*Math.abs(data.dailyOrderFactor[day]));
    }



    @Override
    public DroneSubmitResponse solve(Solution solution) {
        SubmitResponse bestResponse = null;
        SubmitResponse oldBestResponse = null;

        Drone bestDrone = null;

        solution.setBagType(this.bagType.getIndex() );
        solution.setMapName(MAP_NAME);
        solution.setRecycleRefundChoice(true);
        solution.setRefundAmount( (int)2 );
        solution.setBagPrice((int) 5 );

        int numberGenerations = 10;
        Drone[] bestDrones = new Drone[(int)Math.ceil(drones.length*0.5)];
        int bestDronesIndex = 0;
        for (int generationIndex = 0; generationIndex < numberGenerations; generationIndex++) {
            //run drones
            for (int i = 0; i < drones.length; i++) {
                //Do calculations for one drone.
                this.drones[i].survivedRound = false;
                List<Integer> orders = new ArrayList<>();
                for (int day = 0; day < DAYS; day++) {
                    orders.add(defaultOrdering(drones[i].getDroneData(), day, bagType));
                }
                solution.setOrders(orders);

                System.out.printf("%d%n",i);
                SubmitResponse submitResponse = api.submitGame(solution);
                drones[i].score = submitResponse.score;
                if (bestResponse == null || submitResponse.score > bestResponse.score ) { //determine best drone out of current batch.
                    bestDrone = drones[i];
                    bestResponse = submitResponse;
                    bestDrones[bestDronesIndex] = bestDrone;
                    bestDrone.survivedRound = true;
                    bestDronesIndex = bestDronesIndex + (bestDronesIndex % bestDrones.length);
                }
            }
            System.out.println("Your score is: " + bestResponse.score);
            DroneData bestData = bestDrone.getDroneData();


            Arrays.sort(drones, Comparator.comparingDouble(drone -> drone.score)); //TODO: MIGHT CAUSE ISSUES.

            //reproduce using best drone.
            for (int i = 0; i < drones.length; i++) {
                boolean shouldIncreaseMutation = oldBestResponse != null && oldBestResponse.score == bestResponse.score;
                drones[i].cloneFromBetterDrone(bestData,shouldIncreaseMutation);
            }
            /*for (int i = 0; i < (int)Math.floor(drones.length*0.5); i++) {
                //int dominantDroneIndex = ThreadLocalRandom.current().nextInt(0, bestDrones.length); //inclusive, exclusive. random number in range.
                Drone oneOfTheBest = drones[(int)Math.ceil(drones.length*0.5) + i];
                boolean shouldIncreaseMutation = oldBestResponse != null && oldBestResponse.score == bestResponse.score;
                if (drones[i].survivedRound) {
                    drones[i].produceOffspring(bestData,shouldIncreaseMutation);
                } else if (drones[i] != oneOfTheBest) {
                    drones[i].cloneFromBetterDrone(bestData,shouldIncreaseMutation);
                }
            }*/
            oldBestResponse = bestResponse;
        }
        return new DroneSubmitResponse(bestResponse,bestDrone);
    }
}
