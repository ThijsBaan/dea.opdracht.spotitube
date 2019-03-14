package nl.thijs.dea.controllers;

import nl.thijs.dea.connector.DatabaseConnection;
import nl.thijs.dea.dto.LoginRequestDto;
import nl.thijs.dea.dto.LoginResponseDto;
import nl.thijs.dea.dummy.DummyUsers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * LoginController --- class for letting user log in to the application
 *
 * @author Thijs
 */
@Path("/")
@Produces("application/json")
public class LoginController {
    private List<DummyUsers> users = new ArrayList<>();
    private DatabaseConnection conc;

    /**
     * Constructor:
     * Make a new object for the database connection.
     */
    public LoginController() {
        conc = new DatabaseConnection();
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
    public Response login(LoginRequestDto request) throws SQLException {
        var connection = conc.getConnection();

        // Pak de results van de login uit de database
        ResultSet resultLoginSet = doSelectLogin(request, connection);

        if (resultLoginSet.next()) {
            LoginResponseDto response = new LoginResponseDto();

            // Genereer een random token
            String token = randomTokenGenerator();
            // Voeg een token toe aan de database
            insertTokenWithUser(request.getUser(), connection, token);
            response.setToken(token);
            response.setUser(response.makeFullname(request.getUser(),request.getPassword()));

            return Response.ok().entity(response).build();
        }
        return Response.status(401).build();
    }

    /**
     * @param request LoginRequestDto object that contains Username and Password
     * @param connection connection to the database
     * @return a row executed PreparedStatement for the login with the given params
     * @throws SQLException if something went wrong, throw Exception
     */
    private ResultSet doSelectLogin(LoginRequestDto request, Connection connection) throws SQLException {
        PreparedStatement loginSt = connection.prepareStatement("SELECT Username, Password FROM Login WHERE Username " +
                "= ? " +
                "AND Password = ?");
        loginSt.setString(1, request.getUser());
        loginSt.setString(2, request.getPassword());

        return loginSt.executeQuery();
    }

    /**
     * @param username String that contains the users Username
     * @param connection connection to the database
     * @param token String that contains a random token
     * @throws SQLException if something went wrong, throw Exception
     */
    private void insertTokenWithUser(String username, Connection connection, String token) throws SQLException {
        PreparedStatement tokenSt = connection.prepareStatement("INSERT INTO Token (Username, Token)" +
                "VALUES(?, ?)");
        tokenSt.setString(1, username);
        tokenSt.setString(2, token);

        tokenSt.executeUpdate();
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
