package org.macnair.pension.application;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class RootResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response root() {
        return Response.ok(
                RootResource.class.getResourceAsStream("/META-INF/resources/index.html")
        ).build();
    }
}
