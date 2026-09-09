package org.macnair.pension.domain;

public class HistoricalRate {

    private final int year;
    private final double growthRate;
    private final double interestRate;

    public HistoricalRate(int year, double growthRate, double interestRate) {
        this.year = year;
        this.growthRate = growthRate;
        this.interestRate = interestRate;
    }

    public int getYear() {
        return year;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
