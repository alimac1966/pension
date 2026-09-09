package org.macnair.pension.application;

import org.macnair.pension.domain.StressTestResult;
import org.macnair.pension.domain.StressTestSummary;
import org.macnair.pension.infrastructure.dto.StressTestResultDTO;

import jakarta.enterprise.context.ApplicationScoped;
import org.macnair.pension.infrastructure.dto.StressTestSummaryDTO;

@ApplicationScoped
public class StressResultMapper {

    public StressTestResultDTO toDTO(StressTestResult result) {
        StressTestResultDTO dto = new StressTestResultDTO();
        dto.startYear = result.getStartYear();
        dto.monthsUntilDepletion = result.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = result.getYearsUntilDepletion();
        return dto;
    }

    public StressTestSummaryDTO toSummaryDTO(StressTestSummary summary) {
        StressTestSummaryDTO dto = new StressTestSummaryDTO();
        dto.startYear = summary.getStartYear();
        dto.monthsUntilDepletion = summary.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = summary.getYearsUntilDepletion();
        return dto;
    }

}
