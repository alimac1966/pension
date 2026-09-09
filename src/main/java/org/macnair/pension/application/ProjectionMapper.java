package org.macnair.pension.application;

import org.macnair.pension.domain.PensionProjection;
import org.macnair.pension.infrastructure.dto.PensionProjectionDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectionMapper {

    public PensionProjectionDTO toDTO(PensionProjection projection) {
        PensionProjectionDTO dto = new PensionProjectionDTO();
        dto.monthsUntilDepletion = projection.getMonthsUntilDepletion();
        dto.yearsUntilDepletion = projection.getYearsUntilDepletion();
        return dto;
    }
}
