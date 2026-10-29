package org.macnair.pension.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.macnair.pension.domain.ProjectionDomain;
import org.macnair.pension.domain.ProjectionSummary;
import org.macnair.pension.infrastructure.dto.ProjectionRequestDTO;
import org.macnair.pension.infrastructure.dto.ProjectionSummaryDTO;

@ApplicationScoped
public class ProjectionMapper {

    public ProjectionDomain toDomain(ProjectionRequestDTO dto) {
        ProjectionDomain domain = new ProjectionDomain();
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

    public ProjectionSummaryDTO toSummaryDTO(ProjectionSummary summary) {
        return new ProjectionSummaryDTO(
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
