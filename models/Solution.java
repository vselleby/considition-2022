package models;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public boolean recycleRefundChoice;
    public Integer bagPrice;
    public Integer refundAmount;
    public Integer bagType;
    public List<Integer> orders = new ArrayList<>();

    public Solution() {
    }

    // true = refund decreasing with time, false = penalty after expiration time
    public void setRecycleRefundChoice(boolean recycleRefundChoice) {
        this.recycleRefundChoice = recycleRefundChoice;
    }

    public void setBagPrice(Integer bagPrice) {
        this.bagPrice = bagPrice;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setBagType(Integer bagType) {
        this.bagType = bagType;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }
}



