package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.TokenModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDAO {
    private Connection connection;
    private String token;

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
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
                var tk = new TokenModel();

                token = tk.randomTokenGenerator();

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

            return (this.token == null || this.token.equals(""));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyClientToken(String token) {
        try {
            PreparedStatement tokenSt = connection.prepareStatement("SELECT Username, Token FROM Token WHERE " +
                    "Token " +
                    "= ? ");
            tokenSt.setString(1, token);

            return tokenSt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getToken() {
        return this.token;
    }
}
