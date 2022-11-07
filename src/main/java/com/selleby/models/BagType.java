package com.selleby.models;

public enum BagType {
    ONE(1, 1.7, 0, 1, 3, 30),
    TWO(2, 1.75, 1, 2, 4.2, 24),
    THREE(3, 6.0, 5, 3, 1.8, 36),
    FOUR(4, 25.0, 9, 5, 3.6, 42),
    FIVE(5, 200.0, 12, 7, 12, 60);

    private final int index;
    private final double price;
    private final int reuses;
    private final int washTimeInDays;
    private final double transportEmissions;
    private final int productionEmissions;

    BagType(int index, double price, int reuses, int washTimeInDays, double transportEmissions, int productionEmissions) {
        this.index = index;
        this.price = price;
        this.reuses = reuses;
        this.washTimeInDays = washTimeInDays;
        this.transportEmissions = transportEmissions;
        this.productionEmissions = productionEmissions;
    }

    public int getIndex() {
        return index;
    }

    public double getPrice() {
        return price;
    }

    public int getReuses() {
        return reuses;
    }

    public int getWashTimeInDays() {
        return washTimeInDays;
    }

    public double getTransportEmissions() {
        return transportEmissions;
    }

    public int getProductionEmissions() {
        return productionEmissions;
    }

    public static BagType getBagTypeFromIndex(int index) {
        for (BagType bagType : BagType.values()) {
            if (bagType.getIndex() == index) {
                return bagType;
            }
        }
        throw new IllegalArgumentException("No BagType defined with index: " + index);
    }
}
