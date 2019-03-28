package nl.thijs.dea.controllers;

import nl.thijs.dea.services.LoginService;
import nl.thijs.dea.services.dto.LoginRequestDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * LoginController --- class for letting user log in to the application
 *
 * @author Thijs
 */
@Path("/")
@Produces("application/json")
public class LoginController {
    private LoginService loginService;

    @Inject
    public void setService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Let the user login if the user is authorized.
     *
     * @param request an LoginRequestDto object that contains
     *                an Username and Password.
     * @return response with token and username
     */
    @POST
    @Path("login")
    public Response login(LoginRequestDto request) {
        var response = loginService.login(request);

        if (response == null) {
            return Response.status(401).build();
        }

        return Response.ok().entity(response).build();
    }
}
