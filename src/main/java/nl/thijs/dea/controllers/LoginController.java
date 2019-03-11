package nl.thijs.dea.controllers;

import nl.thijs.dea.dto.LoginRequestDto;
import nl.thijs.dea.dto.LoginResponseDto;
import nl.thijs.dea.dummy.DummyUsers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * LoginController --- class for letting user log in to the application
 * @author Thijs
 */
@Path("/")
public class LoginController {
    private List<DummyUsers> users = new ArrayList<>();

    /**
     * Constructor:
     * add two DummyUsers for LoginController and add them to the ArrayList users.
     */
    public LoginController() {
        users.add(new DummyUsers("thijs", "baan"));
        users.add(new DummyUsers("piet", "jansen"));
    }

    /**
     * Let the user login if the user is authorized.
     * @param request an LoginRequestDto object that contains
     *                an Username and Password.
     * @exception 401: Unauthorized. Authorization has failed.
     * @return response with token and username
     */
    @POST
    @Path("login")
    @Produces("application/json")
    public Response login(LoginRequestDto request){

        for (DummyUsers u : users) {
            if (u.getUsername().equals(request.getUser()) && u.getPassword().equals(request.getPassword())){
                LoginResponseDto response = new LoginResponseDto();

                String token = randomTokenGenerator();
                String user = u.getFullName();

                response.setToken(token);
                response.setUser(user);

                return Response.ok().entity(response).build();
            }
        }

        // 401: Unauthorized. Authorization has failed. This can happen
        // if the user tried to log in, but supplied an invalid username/password.
        return Response.status(401).build();
    }

    /**
     * Generates a string with the length of "tokenLength"
     * and containts the SALTCHARS-characters.
     * @return random generated string with specific length
     */
    private String randomTokenGenerator() {
//        final int tokenLength = 15;
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//
//        while (salt.length() < tokenLength) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//
//        return salt.toString();
        return "ABCDEFG";
    }
}
