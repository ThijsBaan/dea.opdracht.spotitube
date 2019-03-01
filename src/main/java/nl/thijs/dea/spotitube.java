package nl.thijs.dea;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class spotitube {

    @GET
    public String helloWorld(){
        return "Hello World";
    }
}
