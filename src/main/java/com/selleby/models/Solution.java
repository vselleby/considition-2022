package com.selleby.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Solution {
    public boolean recycleRefundChoice;
    public Integer bagPrice;
    public Integer refundAmount;
    public Integer bagType;
    public List<Integer> orders = new ArrayList<>();
    public String mapName;

    public Solution() {
    }

    private Solution(boolean recycleRefundChoice, Integer bagPrice, Integer refundAmount, Integer bagType, String mapName) {
        this.recycleRefundChoice = recycleRefundChoice;
        this.bagPrice = bagPrice;
        this.refundAmount = refundAmount;
        this.bagType = bagType;
        this.mapName = mapName;
    }

    // true = refund decreasing with 1 cost unit per day, false = penalty after expiration time of 14 days.
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

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }

    public Solution copyBaseInformation() {
        return new Solution(this.recycleRefundChoice, this.bagPrice, this.refundAmount, this.bagType, this.mapName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return recycleRefundChoice == solution.recycleRefundChoice && Objects.equals(bagPrice, solution.bagPrice) &&
                Objects.equals(refundAmount, solution.refundAmount) && Objects.equals(bagType, solution.bagType) &&
                Objects.equals(orders, solution.orders) && Objects.equals(mapName, solution.mapName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recycleRefundChoice, bagPrice, refundAmount, bagType, orders, mapName);
    }
}



