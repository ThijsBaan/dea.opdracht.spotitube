package nl.thijs.dea.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class SpotitubeController {

    @GET
    @Produces("application/json")
    public Response emptyFunction() {
        return Response.ok().entity("").build();
    }
}
