package nl.thijs.dea.controllers;

import nl.thijs.dea.datasources.doa.LoginDAO;
import nl.thijs.dea.controllers.dto.LoginRequestDto;
import nl.thijs.dea.controllers.dto.LoginResponseDto;
import nl.thijs.dea.models.UserModel;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * LoginController --- class for letting user log in to the application
 *
 * @author Thijs
 */
@Path("/")
@Produces("application/json")
public class LoginController {
    private LoginDAO loginDAO;

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
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

        if ((!user.getUsername().equals("")) || user.getUsername() != null) {
                LoginResponseDto response = new LoginResponseDto();

                // Genereer een random token
                String token = randomTokenGenerator();
                // Voeg een token toe aan de database
                loginDAO.insertTokenWithUser(user.getUsername(), token);
                response.setToken(token);
                response.setUser(response.makeFullname(user.getUsername(),user.getPassword()));

                return Response.ok().entity(response).build();
            }

        return Response.status(401).build();
    }

    /**
     * Generates a string with the length of "tokenLength"
     * and containts the SALTCHARS-characters.
     *
     * @return random generated string with specific length
     */
    private String randomTokenGenerator() {
        final int TOKENLENGTH = 15;
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < TOKENLENGTH) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }
}
