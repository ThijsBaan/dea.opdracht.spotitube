package nl.thijs.dea.controllers;

import nl.thijs.dea.datasources.dao.LoginDAO;
import nl.thijs.dea.controllers.dto.LoginRequestDto;
import nl.thijs.dea.controllers.dto.LoginResponseDto;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.models.UserModel;

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
    private LoginDAO loginDAO;
    private TokenDAO tokenDAO;

    @Inject
    public void setDAO(LoginDAO loginDAO, TokenDAO tokenDAO) {
        this.loginDAO = loginDAO;
        this.tokenDAO = tokenDAO;
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
        // Pak de results van de login uit de database
        UserModel user = loginDAO.login(request.getUser(), request.getPassword());

        if (user == null) {
            return Response.status(401).build();
        }

        LoginResponseDto response = new LoginResponseDto();

        tokenDAO.insertTokenIfUsersFirstTime(user.getUsername());

        response.setToken(tokenDAO.getToken());
        response.setUser(response.makeFullname(user.getUsername(), user.getPassword()));

        return Response.ok().entity(response).build();
    }
}
