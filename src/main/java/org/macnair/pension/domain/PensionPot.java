package org.macnair.pension.domain;

import java.math.BigDecimal;

public class PensionPot {

    private final BigDecimal currentBalance;
    private final double annualGrowthRate; // e.g. 0.06 for 6%

    private PensionPot(Builder builder) {
        this.currentBalance = builder.currentBalance;
        this.annualGrowthRate = builder.annualGrowthRate;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public double getAnnualGrowthRate() {
        return annualGrowthRate;
    }

    public static class Builder {
        private BigDecimal currentBalance;
        private double annualGrowthRate;

        public Builder currentBalance(BigDecimal currentBalance) {
            this.currentBalance = currentBalance;
            return this;
        }

        public Builder annualGrowthRate(double annualGrowthRate) {
            this.annualGrowthRate = annualGrowthRate;
            return this;
        }

        public PensionPot build() {
            return new PensionPot(this);
        }
    }
}
