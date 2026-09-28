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
        int age = currentYear - 1966 + 1;

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

        double taxAllowance = d.taxAllowance;
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
        row.incomeStatePension = baseStatePension;
        row.incomeOther = incomeOther;
        results.add(mapper.toSummaryDTO(row));

        age++;

        while (tfp > 0 || tp > 0) {

            baseStatePension = baseStatePension * (1 + d.inflation); // Adjust state pension for inflation

            // Determine income needed

            // 1. Calculate tax-free pension
            if (tfp > 0) {
                    tfp = (tfp - incomeTFPension) * growthFactor; // Apply growth to remaining tax-free pension
            }

            // 2. Calculate taxable pension
            if (spending > 0 && tp > 0) {
                tp = (tp - incomeTaxPension ) * growthFactor; // Apply growth to remaining taxable pension
            }

            // 3. Calculate Spending
            if (spending > 0 && tp > 0) {
                spending *= inflationFactor; // Apply growth to spending
            }

            // 4. Calculate State pension (if age >= 67)
            incomeStatePension = (age >= 67) ? baseStatePension : 0;

            // 5. Calculate Income from Pension

            if (tfp > (spending - taxAllowance )) {
                incomeTFPension = (spending - taxAllowance);
                incomeTaxPension = taxAllowance;
            } else {
                incomeTFPension = tfp;
                incomeTaxPension = (spending - tfp - incomeStatePension - incomeOther) * 1.25;

            }


        //    incomeTaxPension = (tfp > (spending - taxAllowance ) ? taxAllowance : (spending - incomeTFPension - incomeStatePension - incomeOther));

            // 6. Calculate Tax Free Income from Pension
        //    incomeTFPension = (tfp > (spending - taxAllowance) ? (spending - taxAllowance) : tfp);


            // Build row

            row.myAge = age;
            row.taxFreePension = tfp;
            row.taxablePension = tp;
            row.spending = spending;
            row.incomeTFPension = incomeTFPension;
            row.incomeTaxPension = incomeTaxPension;
            row.incomeStatePension = baseStatePension;
            row.incomeOther = incomeOther;

            results.add(mapper.toSummaryDTO(row));

            // Apply growth to remaining pension
//            tfp *= growthFactor;
//            tp  *= growthFactor;

            // Apply inflation to spending and state pension
//            spending *= inflationFactor;
//            incomeStatePension *= inflationFactor;

            // Age increases
            age++;

            // Stop at age 100 to avoid infinite loops
            if (age > 100) break;

        }

        return results;
    }
}
