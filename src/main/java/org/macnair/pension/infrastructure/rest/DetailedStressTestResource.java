package org.macnair.pension.infrastructure.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.application.DetailedStressTestService;
import org.macnair.pension.infrastructure.dto.*;

import java.util.List;

@Path("/pension")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetailedStressTestResource {

    @Inject
    DetailedStressTestService stressTestService;

    @POST
    @Path("/stress-test/detailed")
    public List<DetailedStressTestSummaryDTO> runAllYears(DetailedStressTestRequestDTO request) {
        return stressTestService.runStressTestAllYears(request);
    }
}
