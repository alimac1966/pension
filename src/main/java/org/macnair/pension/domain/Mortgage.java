package org.macnair.pension.domain;

import java.math.BigDecimal;

public class Mortgage {

    private final BigDecimal outstandingBalance;
    private final double annualInterestRate;
    private final BigDecimal monthlyPayment;

    private Mortgage(Builder builder) {
        this.outstandingBalance = builder.outstandingBalance;
        this.annualInterestRate = builder.annualInterestRate;
        this.monthlyPayment = builder.monthlyPayment;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public static class Builder {
        private BigDecimal outstandingBalance;
        private double annualInterestRate;
        private BigDecimal monthlyPayment;

        public Builder outstandingBalance(BigDecimal outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
            return this;
        }

        public Builder annualInterestRate(double annualInterestRate) {
            this.annualInterestRate = annualInterestRate;
            return this;
        }

        public Builder monthlyPayment(BigDecimal monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
            return this;
        }

        public Mortgage build() {
            return new Mortgage(this);
        }
    }
}
