package nl.thijs.dea.controllers;

import nl.thijs.dea.connector.DatabaseConnection;
import nl.thijs.dea.dto.LoginRequestDto;
import nl.thijs.dea.dto.LoginResponseDto;
import nl.thijs.dea.dummy.DummyUsers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
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
        PreparedStatement st = connection.prepareStatement("SELECT Username, Password FROM Login WHERE Username = ? " +
                "AND Password = ?");
        st.setString(1, request.getUser());
        st.setString(2, request.getPassword());

        ResultSet resultSet = st.executeQuery();

        if (resultSet.next()) {
            LoginResponseDto response = new LoginResponseDto();

            var u = new DummyUsers(request.getUser(), request.getPassword());

            String token = randomTokenGenerator();
            String user = u.getFullName();

            response.setToken(token);
            response.setUser(user);

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
//        final int TOKENLENGTH = 15;
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//
//        while (salt.length() < TOKENLENGTH) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//
//        return salt.toString();
        return "ABCDEFG";
    }
}
