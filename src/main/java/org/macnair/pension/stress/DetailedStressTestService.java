package org.macnair.pension.stress;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DetailedStressTestService {

    @Inject
    DetailedStressTestMapper mapper;

    public List<DetailedStressTestSummaryDTO> runDetailedYears(DetailedStressTestRequestDTO req) {

        DetailedStressTestDomain d = mapper.toDomain(req);

        List<DetailedStressTestSummaryDTO> results = new ArrayList<>();

        int currentYear = LocalDate.now().getYear();
        int age = currentYear - 1966;

        // Split pension into tax-free (25%) and taxable (75%)
        double tfp = d.pensionBalance * 0.25;
        double tp  = d.pensionBalance * 0.75;

        double mortgage = d.mortgageBalance;
        tfp = tfp - mortgage + d.savings; // Use tax-free pension to pay off mortgage first
        mortgage = 0; // Mortgage is paid off


        double spending = d.spending;
        double baseStatePension = d.statePension;
        double incomeState =  d.statePension ;
        double incomeOther = 0;

        // Growth and inflation must be applied as (1 + rate)
        double growthFactor = 1 + d.growthRate;
        double inflationFactor = 1 + d.inflation;


        double incomeTaxPension = d.taxAllowance;
        double incomeStatePension =  (age >= 67) ? baseStatePension : 0;
        double incomeTFPension = spending - incomeTaxPension - incomeStatePension - incomeOther;


        // Build row
        DetailedStressTestSummary row = new DetailedStressTestSummary();
        row.myAge = age;
        row.taxFreePension = tfp;
        row.taxablePension = tp;
        row.spending = spending;
        row.incomeTFPension = incomeTFPension;
        row.incomeTaxPension = incomeTaxPension;
        row.incomeStatePension = incomeStatePension;
        row.incomeOther = incomeOther;
        results.add(mapper.toSummaryDTO(row));

        age++;

        while (tfp > 0 || tp > 0) {

            // Determine income needed
            double remainingSpending = spending;

//            double incomeTFPension = 0;
//            double incomeTaxPension = 0;

            // 1. Use tax-free pension first
            if (tfp > 0) {
                double tfpWithdrawal = Math.min(tfp, remainingSpending);
                incomeTFPension = tfpWithdrawal;
                remainingSpending -= tfpWithdrawal;
                tfp -= tfpWithdrawal;
            }

            // 2. Use taxable pension up to tax allowance
            if (remainingSpending > 0 && tp > 0) {
                double taxableUpToAllowance = Math.min(d.taxAllowance, remainingSpending);
                double tpWithdrawal = Math.min(tp, taxableUpToAllowance);
                incomeTaxPension = tpWithdrawal;
                remainingSpending -= tpWithdrawal;
                tp -= tpWithdrawal;
            }

            // 3. Use taxable pension above allowance (fully taxable)
            if (remainingSpending > 0 && tp > 0) {
                double tpWithdrawal = Math.min(tp, remainingSpending);
                incomeTaxPension += tpWithdrawal;
                remainingSpending -= tpWithdrawal;
                tp -= tpWithdrawal;
            }

            // 4. State pension (if age >= 67)
             incomeStatePension = (age >= 67) ? baseStatePension : 0;

            // Build row

            row.myAge = age;
            row.taxFreePension = tfp;
            row.taxablePension = tp;
            row.spending = spending;
            row.incomeTFPension = incomeTFPension;
            row.incomeTaxPension = incomeTaxPension;
            row.incomeStatePension = incomeStatePension;
            row.incomeOther = incomeOther;

            results.add(mapper.toSummaryDTO(row));

            // Apply growth to remaining pension
            tfp *= growthFactor;
            tp  *= growthFactor;

            // Apply inflation to spending and state pension
            spending *= inflationFactor;
            incomeState *= inflationFactor;

            // Age increases
            age++;

            // Stop at age 100 to avoid infinite loops
            if (age > 100) break;
            baseStatePension = baseStatePension * (1 + d.inflation); // Adjust state pension for inflation
        }

        return results;
    }
}
