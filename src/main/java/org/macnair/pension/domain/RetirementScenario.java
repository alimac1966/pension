package org.macnair.pension.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RetirementScenario {

    private final PensionPot pensionPot;
    private final Mortgage mortgage;
    private final BigDecimal monthlyDrawdown;
    private final LocalDate retirementStartDate;

    private RetirementScenario(Builder builder) {
        this.pensionPot = builder.pensionPot;
        this.mortgage = builder.mortgage;
        this.monthlyDrawdown = builder.monthlyDrawdown;
        this.retirementStartDate = builder.retirementStartDate;
    }

    public PensionPot getPensionPot() {
        return pensionPot;
    }

    public Mortgage getMortgage() {
        return mortgage;
    }

    public BigDecimal getMonthlyDrawdown() {
        return monthlyDrawdown;
    }

    public LocalDate getRetirementStartDate() {
        return retirementStartDate;
    }

    public static class Builder {
        private PensionPot pensionPot;
        private Mortgage mortgage;
        private BigDecimal monthlyDrawdown;
        private LocalDate retirementStartDate;

        public Builder pensionPot(PensionPot pensionPot) {
            this.pensionPot = pensionPot;
            return this;
        }

        public Builder mortgage(Mortgage mortgage) {
            this.mortgage = mortgage;
            return this;
        }

        public Builder monthlyDrawdown(BigDecimal monthlyDrawdown) {
            this.monthlyDrawdown = monthlyDrawdown;
            return this;
        }

        public Builder retirementStartDate(LocalDate retirementStartDate) {
            this.retirementStartDate = retirementStartDate;
            return this;
        }

        public RetirementScenario build() {
            return new RetirementScenario(this);
        }
    }
}
