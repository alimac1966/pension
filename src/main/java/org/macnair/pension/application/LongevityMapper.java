package org.macnair.pension.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.macnair.pension.domain.LongevityDomain;
import org.macnair.pension.infrastructure.dto.LongevityRequestDTO;
import org.macnair.pension.infrastructure.dto.LongevitySummaryDTO;
import org.macnair.pension.domain.LongevitySummary;

@ApplicationScoped
public class LongevityMapper {

    public LongevityDomain toDomain(LongevityRequestDTO dto) {
        LongevityDomain domain = new LongevityDomain();
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

    public LongevitySummaryDTO toSummaryDTO(LongevitySummary summary) {
        return new LongevitySummaryDTO(
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
