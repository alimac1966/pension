package org.macnair.pension.infrastructure.rest;

import org.macnair.pension.application.PensionService;
import org.macnair.pension.infrastructure.dto.PensionRequestDTO;
import org.macnair.pension.infrastructure.dto.PensionProjectionDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/pension")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PensionResource {

    @Inject
    PensionService pensionService;

    @POST
    @Path("/projection")
    public PensionProjectionDTO calculateProjection(PensionRequestDTO request) {

        validate(request);

        return pensionService.calculateProjection(request);
    }

    private void validate(PensionRequestDTO request) {
        if (request.pensionBalance <= 0) {
            throw new BadRequestException("Pension balance must be greater than zero.");
        }
        if (request.monthlyDrawdown < 0) {
            throw new BadRequestException("Monthly drawdown cannot be negative.");
        }
        if (request.mortgageMonthlyPayment < 0) {
            throw new BadRequestException("Mortgage payment cannot be negative.");
        }
    }
}
