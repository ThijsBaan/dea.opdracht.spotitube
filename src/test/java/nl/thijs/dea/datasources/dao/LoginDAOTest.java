package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LoginDAOTest {

    private static final String USERNAME = "thijs";
    private static final String WACHTWOORD = "baan";
    private static final String FOUT_WACHTWOORD = "FOUT";

    private LoginDAO sut;
    private DatabaseConnection dbConnection;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private UserModel user;

    @BeforeEach
    void setup() throws SQLException {
        sut = new LoginDAO();
        user = new UserModel(USERNAME, WACHTWOORD);

        dbConnection = mock(DatabaseConnection.class);

        connection = Mockito.mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);

        when(statement.executeQuery()).thenReturn(result);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(dbConnection.getConnection()).thenReturn(connection);

        sut.setConnection(dbConnection);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDbConnection() throws SQLException {
        // Setup

        // Test
        sut.login(USERNAME, WACHTWOORD);
        // Verify
        verify(connection).prepareStatement(anyString());
    }

    @Test
    void doesMethodLoginUsesSetString() throws SQLException {
        //Setup

        //Test
        sut.login(USERNAME, WACHTWOORD);

        //Verify
        verify(statement).setString(1, USERNAME);
    }

    @Test
    void doesMethodloginExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.login(USERNAME, WACHTWOORD);

        //Verify
        verify(statement).executeQuery();
    }


    @Test
    void doesMethodLoginReturnCorrectUser() throws SQLException {
        //Setup
        when(statement.executeQuery().next()).thenReturn(true);

        //Test
        UserModel realUser = sut.login(USERNAME, WACHTWOORD);

        //Verify
        assertEquals(user.getUsername(), realUser.getUsername());
        assertEquals(user.getPassword(), realUser.getPassword());
    }

    @Test
    void doesMethodLoginReturnCNullWhenLoginIsIncorrect() throws SQLException {
        //Setup
        when(statement.executeQuery().next()).thenReturn(false);

        //Test
        UserModel realUser = sut.login(USERNAME, FOUT_WACHTWOORD);

        //Verify
        assertNull(realUser);
    }

    @Test
    void doSQLExceptionGetsCorrectHandling(){
        // MOET NOG GEMAAKT WORDEN!!!

        fail();
    }
}