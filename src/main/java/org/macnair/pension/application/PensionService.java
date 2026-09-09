package org.macnair.pension.application;


import org.macnair.pension.domain.PensionCalculator;
import org.macnair.pension.domain.PensionProjection;
import org.macnair.pension.infrastructure.dto.PensionProjectionDTO;
import org.macnair.pension.infrastructure.dto.PensionRequestDTO;
import org.macnair.pension.domain.RetirementScenario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PensionService {

    @Inject
    PensionCalculator calculator;

    @Inject
    ScenarioMapper scenarioMapper;

    @Inject
    ProjectionMapper projectionMapper;

    public PensionProjectionDTO calculateProjection(PensionRequestDTO request) {
        RetirementScenario scenario = scenarioMapper.toDomain(request);
        PensionProjection projection = calculator.project(scenario);
        return projectionMapper.toDTO(projection);
    }
}
