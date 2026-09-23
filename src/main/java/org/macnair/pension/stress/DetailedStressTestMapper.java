package org.macnair.pension.stress;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DetailedStressTestMapper {

    public DetailedStressTestDomain toDomain(DetailedStressTestRequestDTO dto) {
        DetailedStressTestDomain domain = new DetailedStressTestDomain();
        domain.pensionBalance = dto.pensionBalance;
        domain.growthRate = dto.growthRate;
        domain.inflation = dto.inflation;
        domain.mortgageBalance = dto.mortgageBalance;
        domain.spending = dto.spending;
        domain.savings = dto.savings;
        domain.taxAllowance = dto.taxAllowance;
        domain.statePension = dto.statePension;
        return domain;
    }

    public DetailedStressTestSummaryDTO toSummaryDTO(DetailedStressTestSummary summary) {
        return new DetailedStressTestSummaryDTO(
                summary.myAge,
                summary.taxFreePension,
                summary.taxablePension,
                summary.spending,
                summary.incomeTFPension,
                summary.incomeTaxPension,
                summary.incomeStatePension,
                summary.incomeOther
        );
    }
}
