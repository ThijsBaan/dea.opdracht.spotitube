package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.UserModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class LoginDAO {
    private Connection connection;
    private String token;

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public UserModel login(String user, String password) {
        if (verifyLogin(user, password)) {
            return new UserModel(user, password);
        }
        return null;
    }

    /**
     * @param user     the username that was used in the userform.
     * @param password the password that was used in the loginform.
     * @return a row executed PreparedStatement for the login with the given params
     */
    private boolean verifyLogin(String user, String password) {
        try {
            PreparedStatement loginSt = connection.prepareStatement("SELECT Username, Password FROM Login WHERE " +
                    "Username " +
                    "= ? " +
                    "AND Password = ?");
            loginSt.setString(1, user);
            loginSt.setString(2, password);

            return loginSt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if hasntUserGotATokenYet(username) returns false,
     * when it is false, generate a random Token and insert this into the database
     *
     * @param username String that contains the users Username
     */
    public void insertTokenIfUsersFirstTime(String username) {
        try {
            if (hasntUserGotATokenYet(username)) {
                token = randomTokenGenerator();
                PreparedStatement tokenSt = connection.prepareStatement("INSERT INTO Token (Username, Token)" +
                        "VALUES(?, ?)");
                tokenSt.setString(1, username);
                tokenSt.setString(2, token);

                tokenSt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    /**
     * Do a select in the table Token with the Username.
     * If there is a result, set the local variable to this result
     * @param username String that contains the users Username
     * @return boolean: true if token has been found otherwise false
     */
    private boolean hasntUserGotATokenYet(String username) {
        try {
            PreparedStatement userTokenSt = connection.prepareStatement("SELECT Token FROM Token WHERE Username = ?");
            userTokenSt.setString(1, username);

            ResultSet r = userTokenSt.executeQuery();

            if (r.next()) {
                this.token = r.getString("Token");
            }

            return "".equals(token);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
