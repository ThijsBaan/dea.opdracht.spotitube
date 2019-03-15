package nl.thijs.dea.datasources.doa;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.UserModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private Connection connection;

    @Inject
    public void setConnection(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public UserModel login(String user, String password){
        if (checkIfLoginIsCorrect(user, password)){
            return new UserModel(user, password);
        }
        return null;
    }

    /**
     * @param user the username that was used in the userform.
     * @param password the password that was used in the loginform.
     * @return a row executed PreparedStatement for the login with the given params
     */
    private boolean checkIfLoginIsCorrect(String user, String password) {
        try {
            PreparedStatement loginSt = connection.prepareStatement("SELECT Username, Password FROM Login WHERE " +
                    "Username " +
                    "= ? " +
                    "AND Password = ?");
            loginSt.setString(1, user);
            loginSt.setString(2, password);

            ResultSet results = loginSt.executeQuery();

            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param username String that contains the users Username
     * @param token    String that contains a random token
     */
    public void insertTokenWithUser(String username, String token) {
        try {
            PreparedStatement tokenSt = connection.prepareStatement("INSERT INTO Token (Username, Token)" +
                    "VALUES(?, ?)");
            tokenSt.setString(1, username);
            tokenSt.setString(2, token);

            tokenSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
