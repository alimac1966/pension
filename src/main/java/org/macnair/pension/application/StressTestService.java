package org.macnair.pension.application;


import org.macnair.pension.domain.*;
import org.macnair.pension.infrastructure.dto.StressTestRequestDTO;
import org.macnair.pension.infrastructure.dto.StressTestResultDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.macnair.pension.infrastructure.dto.StressTestSummaryDTO;

import java.util.List;

@ApplicationScoped
public class StressTestService {

    @Inject
    HistoricalRateService historicalRateService;

    @Inject
    StressTestMapper stressTestMapper;

    @Inject
    StressResultMapper stressResultMapper;

    @Inject
    StressTestEngine stressTestEngine;

    public StressTestResultDTO runStressTest(StressTestRequestDTO request) {

        RetirementScenario scenario = stressTestMapper.toDomain(request);

        List<HistoricalRate> rates = historicalRateService.loadHistoricalRates();

        StressTestResult result = stressTestEngine.run(scenario, rates);

        return stressResultMapper.toDTO(result);
    }
    public List<StressTestSummaryDTO> runStressTestAllYears(StressTestRequestDTO request) {

        RetirementScenario scenario = stressTestMapper.toDomain(request);
        List<HistoricalRate> rates = historicalRateService.loadHistoricalRates();

        List<StressTestSummary> results = stressTestEngine.runAllYears(scenario, rates);

        return results.stream()
                .map(stressResultMapper::toSummaryDTO)
                .toList();
    }

}
