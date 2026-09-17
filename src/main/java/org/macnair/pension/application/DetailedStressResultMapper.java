package org.macnair.pension.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.macnair.pension.domain.DetailedStressTestResult;
import org.macnair.pension.domain.DetailedStressTestSummary;
import org.macnair.pension.infrastructure.dto.DetailedStressTestResultDTO;
import org.macnair.pension.infrastructure.dto.DetailedStressTestSummaryDTO;

@ApplicationScoped
public class DetailedStressResultMapper {

    public static DetailedStressTestResultDTO toDTO(DetailedStressTestResult result) {
        DetailedStressTestResultDTO dto = new DetailedStressTestResultDTO();
        dto.startYear = result.getStartYear();
        dto.monthsUntilDepletion = result.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = result.getYearsUntilDepletion();
        return dto;
    }

    public DetailedStressTestSummaryDTO toDetailedSummaryDTO(DetailedStressTestSummary summary) {
        DetailedStressTestSummaryDTO dto = new DetailedStressTestSummaryDTO();
        dto.startYear = summary.getStartYear();
        dto.monthsUntilDepletion = summary.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = summary.getYearsUntilDepletion();
        return dto;
    }

}
