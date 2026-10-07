package org.macnair.pension.infrastructure.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.infrastructure.dto.LongevityRequestDTO;
import org.macnair.pension.application.LongevityService;
import org.macnair.pension.infrastructure.dto.LongevitySummaryDTO;

import java.util.List;

@Path("/longevity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LongevitytResource {

    @Inject
    LongevityService service;

    @POST
    public List<LongevitySummaryDTO> runDetailedYears(LongevityRequestDTO request) {
        return service.runDetailedYears(request);
    }
}



