package org.macnair.pension.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.macnair.pension.model.CalcResult;
import org.macnair.pension.service.CalcService;

@Path("/calc")
public class CalcResource {

    @Inject
    CalcService calcService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CalcResult calculate(
            @QueryParam("x") Double x,
            @QueryParam("y") Double y) {

        // If no query params provided, fall back to config values
        if (x == null || y == null) {
            return calcService.calculate();
        }

        return calcService.calculateDynamic(x, y);
    }
}
