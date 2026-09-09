package org.macnair.pension.domain;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PensionCalculator {

    public PensionProjection project(RetirementScenario scenario) {

        BigDecimal pot = scenario.getPensionPot().getCurrentBalance();
        double growthRate = scenario.getPensionPot().getAnnualGrowthRate();

        BigDecimal mortgageBalance = scenario.getMortgage().getOutstandingBalance();
        double mortgageInterest = scenario.getMortgage().getAnnualInterestRate();
        BigDecimal mortgagePayment = scenario.getMortgage().getMonthlyPayment();

        BigDecimal drawdown = scenario.getMonthlyDrawdown();

        int months = 0;

        while (pot.compareTo(BigDecimal.ZERO) > 0) {

            // Pension growth
            pot = pot.multiply(BigDecimal.valueOf(1 + (growthRate / 12)));

            // Mortgage interest
            mortgageBalance = mortgageBalance.multiply(BigDecimal.valueOf(1 + (mortgageInterest / 12)));

            // Mortgage payment reduces pot
            pot = pot.subtract(mortgagePayment);

            // Drawdown reduces pot
            pot = pot.subtract(drawdown);

            months++;
        }

        return new PensionProjection(months);
    }
}
