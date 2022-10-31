import models.Solution;
import java.util.*;

public class Solver {
    private static final List<Double>  bagType_price          = [1.7, 1.75, 6, 25, 200]
    private static final List<Integer> bagType_co2_production = [5, 7, 3, 6, 20]
    private static final List<Integer> bagType_co2_transport  = [50, 40, 60, 70, 100]

    private Solution solution;

    public Solver() {
        normalPackages = new ArrayList<Package>();
        heavyPackages = new ArrayList<Package>();
        placedPackages = new ArrayList<Package>();
        solution = new Solution();
    }


    public Solution Solve(int bagtype, int days) {
        solution.setRecycleRefundChoice(true);
        solution.setBagPrice(10);
        solution.setRefundAmount(1);
        solution.setBagType(bagtype);
        
        List<Integer> orders = new ArrayList<>();
        for (int day = 0; day < days, day++) {
            orders.add(wasteMoney(bag_type));
        }
        
        solution.setOrders(orders);
        return solution;
    }

    
    // Solution 1: "Spend all money day 1"
    private static Integer wasteMoney(bagtype):
        return Math.floor(companyBudget / bagType_price[bagtype])

    // Solution 2: "Spend equally money every day"
    private static Integer splitMoney(bagtype):
        return Math.floor(companyBudget / bagType_price[bagtype] / days)

    // Solution 3: "Everyone get one bag every day"
    private static Integer holdMoney(bagtype):
        return Math.floor(companyBudget / bagType_price[bagtype] / population / days)
}