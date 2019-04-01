package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.services.models.UserModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDAO {
    private Connection connection;

    @Inject
    public LoginDAO(DatabaseConnection databaseConnection) {
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
            PreparedStatement loginSt = connection.prepareStatement("SELECT Username, Password FROM Login " +
                    "WHERE Username " +
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
}
