package org.macnair.pension.application;

import org.junit.jupiter.api.Test;
import org.macnair.pension.domain.ProjectionDomain;
import org.macnair.pension.domain.ProjectionSummary;
import org.macnair.pension.infrastructure.dto.ProjectionRequestDTO;
import org.macnair.pension.infrastructure.dto.ProjectionSummaryDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectionMapperTest {

    private final ProjectionMapper mapper = new ProjectionMapper();

    @Test
    void mapsEveryRequestFieldToDomain() {
        ProjectionRequestDTO request = new ProjectionRequestDTO();
        request.pensionBalance = 200_000;
        request.growthRate = 0.04;
        request.inflation = 0.02;
        request.mortgageBalance = 15_000;
        request.spending = 30_000;
        request.savings = 5_000;
        request.taxAllowance = 12_570;
        request.statePension = 10_000;

        ProjectionDomain result = mapper.toDomain(request);

        assertEquals(request.pensionBalance, result.pensionBalance);
        assertEquals(request.growthRate, result.growthRate);
        assertEquals(request.inflation, result.inflation);
        assertEquals(request.mortgageBalance, result.mortgageBalance);
        assertEquals(request.spending, result.spending);
        assertEquals(request.savings, result.savings);
        assertEquals(request.taxAllowance, result.taxAllowance);
        assertEquals(request.statePension, result.statePension);
    }

    @Test
    void mapsEverySummaryFieldToResponse() {
        ProjectionSummary summary = new ProjectionSummary();
        summary.myAge = 61;
        summary.taxFreePension = 20_000;
        summary.taxablePension = 60_000;
        summary.spending = 30_000;
        summary.incomeTFPension = 10_000;
        summary.incomeTaxPension = 12_570;
        summary.incomeStatePension = 9_000;
        summary.incomeOther = 1_000;

        ProjectionSummaryDTO result = mapper.toSummaryDTO(summary);

        assertEquals(summary.myAge, result.myAge);
        assertEquals(summary.taxFreePension, result.taxFreePension);
        assertEquals(summary.taxablePension, result.taxablePension);
        assertEquals(summary.spending, result.spending);
        assertEquals(summary.incomeTFPension, result.incomeTFPension);
        assertEquals(summary.incomeTaxPension, result.incomeTaxPension);
        assertEquals(summary.incomeStatePension, result.incomeStatePension);
        assertEquals(summary.incomeOther, result.incomeOther);
    }
}
