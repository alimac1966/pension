package org.macnair.pension.infrastructure.rest;

import org.macnair.pension.application.StressTestService;
import org.macnair.pension.infrastructure.dto.StressTestRequestDTO;
import org.macnair.pension.infrastructure.dto.StressTestResultDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.infrastructure.dto.StressTestSummaryDTO;

import java.util.List;

@Path("/pension")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StressTestResource {

    @Inject
    StressTestService stressTestService;

    @POST
    @Path("/stress-test")
    public StressTestResultDTO runStressTest(StressTestRequestDTO request) {

        validate(request);

        return stressTestService.runStressTest(request);
    }
    @POST
    @Path("/stress-test/all")
    public List<StressTestSummaryDTO> runAllYears(StressTestRequestDTO request) {
        return stressTestService.runStressTestAllYears(request);
    }

    private void validate(StressTestRequestDTO request) {

        if (request.pensionBalance <= 0) {
            throw new BadRequestException("Pension balance must be greater than zero.");
        }

        if (request.mortgageBalance < 0) {
            throw new BadRequestException("Mortgage balance cannot be negative.");
        }

        if (request.mortgageMonthlyPayment < 0) {
            throw new BadRequestException("Mortgage payment cannot be negative.");
        }

        if (request.monthlyDrawdown < 0) {
            throw new BadRequestException("Monthly drawdown cannot be negative.");
        }
    }
}
