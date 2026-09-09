package org.macnair.pension.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StressTestEngine {

    public StressTestResult run(RetirementScenario scenario, List<HistoricalRate> rates) {

        BigDecimal pot = scenario.getPensionPot().getCurrentBalance();
        BigDecimal mortgageBalance = scenario.getMortgage().getOutstandingBalance();
        BigDecimal mortgagePayment = scenario.getMortgage().getMonthlyPayment();
        BigDecimal drawdown = scenario.getMonthlyDrawdown();

        int months = 0;
        int startYear = rates.get(0).getYear();

        for (HistoricalRate rate : rates) {

            double growthRate = rate.getGrowthRate();
            double interestRate = rate.getInterestRate();

            // simulate 12 months for this year
            for (int m = 0; m < 12; m++) {

                // pension growth
                pot = pot.multiply(BigDecimal.valueOf(1 + (growthRate / 12)));

                // mortgage interest
                mortgageBalance = mortgageBalance.multiply(BigDecimal.valueOf(1 + (interestRate / 12)));

                // mortgage payment reduces pot
                pot = pot.subtract(mortgagePayment);

                // drawdown reduces pot
                pot = pot.subtract(drawdown);

                months++;

                if (pot.compareTo(BigDecimal.ZERO) <= 0) {
                    return new StressTestResult(startYear, months);
                }
            }
        }

        // If pot survives all historical years
        return new StressTestResult(startYear, months);
    }
    public List<StressTestSummary> runAllYears(
            RetirementScenario scenario,
            List<HistoricalRate> rates) {

        List<StressTestSummary> results = new ArrayList<>();

        for (int i = 0; i < rates.size(); i++) {

            int startYear = rates.get(i).getYear();

            // slice the list from this year onward
            List<HistoricalRate> subRates = rates.subList(i, rates.size());

            StressTestResult result = run(scenario, subRates);

            results.add(new StressTestSummary(startYear, result.getMonthsUntilDepletion()));
        }

        return results;
    }

}
