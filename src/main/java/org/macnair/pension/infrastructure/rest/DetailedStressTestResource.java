package org.macnair.pension.infrastructure.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.stress.DetailedStressTestRequestDTO;
import org.macnair.pension.stress.DetailedStressTestService;
import org.macnair.pension.stress.DetailedStressTestSummaryDTO;

import java.util.List;

@Path("/stress-test/detailed")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DetailedStressTestResource {

    @Inject
    DetailedStressTestService service;

    @POST
    public List<DetailedStressTestSummaryDTO> runDetailedYears(DetailedStressTestRequestDTO request) {
        return service.runDetailedYears(request);
    }
}



