package nl.thijs.dea;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class Spotitube {


    @GET
    @Produces("application/json")
    public Response hello() {
        return Response.ok().entity("Hello World").build();
    }
}
