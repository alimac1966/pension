package org.macnair.pension.infrastructure.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.application.ProjectionService;
import org.macnair.pension.infrastructure.dto.ProjectionRequestDTO;
import org.macnair.pension.infrastructure.dto.ProjectionSummaryDTO;

import java.util.List;

@Path("/projection")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectionResource {

    @Inject
    ProjectionService service;

    @POST
    public List<ProjectionSummaryDTO> runDetailedYears(ProjectionRequestDTO request) {
        return service.runDetailedYears(request);
    }
}



