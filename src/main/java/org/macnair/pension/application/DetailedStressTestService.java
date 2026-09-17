package org.macnair.pension.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.macnair.pension.domain.DetailedStressTestEngine;
import org.macnair.pension.infrastructure.dto.*;

import java.util.List;

@ApplicationScoped
public class DetailedStressTestService {

    @Inject
    DetailedStressTestMapper detailedStressTestMapper;

    @Inject
    DetailedStressTestEngine engine;

    public List<DetailedStressTestSummaryDTO> runStressTestAllYears(DetailedStressTestRequestDTO request) {

        var domain = detailedStressTestMapper.toDomain(request);

        var results = engine.runAllYears(domain);

        return results.stream()
                .map(detailedStressTestMapper::toDetailedSummaryDTO)
                .toList();
    }
}
