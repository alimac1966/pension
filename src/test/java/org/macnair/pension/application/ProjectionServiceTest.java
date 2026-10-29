package org.macnair.pension.application;

import org.junit.jupiter.api.Test;
import org.macnair.pension.infrastructure.dto.ProjectionRequestDTO;
import org.macnair.pension.infrastructure.dto.ProjectionSummaryDTO;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectionServiceTest {

    private final ProjectionService service = new ProjectionService();

    ProjectionServiceTest() {
        service.mapper = new ProjectionMapper();
    }

    @Test
    void returnsStartingProjectionWhenThereIsNoPensionBalance() {
        ProjectionRequestDTO request = request();
        request.spending = 30_000;
        request.taxAllowance = 12_570;

        List<ProjectionSummaryDTO> result = service.runDetailedYears(request);

        assertEquals(1, result.size());
        ProjectionSummaryDTO first = result.get(0);
        assertEquals(Year.now().getValue() - 1966 + 1, first.myAge);
        assertEquals(0, first.taxFreePension);
        assertEquals(0, first.taxablePension);
        assertEquals(30_000, first.spending);
        assertEquals(17_430, first.incomeTFPension);
        assertEquals(12_570, first.incomeTaxPension);
    }

    @Test
    void allocatesPensionAndPaysMortgageFromTaxFreeBalance() {
        ProjectionRequestDTO request = request();
        request.pensionBalance = 200_000;
        request.mortgageBalance = 10_000;
        request.savings = 5_000;
        request.spending = 30_000;
        request.taxAllowance = 12_000;
        request.growthRate = 0.04;

        List<ProjectionSummaryDTO> result = service.runDetailedYears(request);

        assertTrue(result.size() > 1);
        ProjectionSummaryDTO first = result.get(0);
        assertEquals(45_000, first.taxFreePension);
        assertEquals(150_000, first.taxablePension);
        assertEquals(18_000, first.incomeTFPension);

        ProjectionSummaryDTO second = result.get(1);
        assertEquals(45_000 - 18_000, second.taxFreePension / 1.04, 0.0001);
        assertEquals((150_000 - 12_000) * 1.04, second.taxablePension, 0.0001);
        assertEquals(first.myAge + 1, second.myAge);
    }

    @Test
    void capsProjectionAtAgeOneHundredWhenAssetsRemain() {
        ProjectionRequestDTO request = request();
        request.pensionBalance = 1_000_000_000;
        request.growthRate = 0;
        request.spending = 0;
        request.taxAllowance = 0;

        List<ProjectionSummaryDTO> result = service.runDetailedYears(request);

        assertEquals(100, result.get(result.size() - 1).myAge);
        assertTrue(result.size() <= 100);
    }

    private static ProjectionRequestDTO request() {
        return new ProjectionRequestDTO();
    }
}
