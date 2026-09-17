package org.macnair.pension.domain;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DetailedStressTestEngine {

    public List<DetailedStressTestSummary> runAllYears(DetailedStressTest domain) {

        List<DetailedStressTestSummary> summaries = new ArrayList<>();

        for (int year = domain.getStartYear(); year <= domain.getStartYear() + 40; year++) {

            double balance = domain.getMortgageBalance();
            List<MonthlyResult> monthly = new ArrayList<>();

            int months = 0;

            while (balance > 0 && months < 600) {
                balance += balance * (domain.getDefaultGrowthRate() / 12.0);
                balance -= domain.getMonthlyDrawdown();
                balance -= domain.getMortgageMonthlyPayment();

                monthly.add(new MonthlyResult(months + 1, balance));
                months++;
            }

            DetailedStressTestSummary summary = new DetailedStressTestSummary();
            summary.setStartYear(year);
            summary.setMonthsUntilDepletion(months);
            summary.setYearsUntilDepletion(months / 12);
            summary.setMonthlyBreakdown(monthly);

            summaries.add(summary);
        }

        return summaries;
    }
}
