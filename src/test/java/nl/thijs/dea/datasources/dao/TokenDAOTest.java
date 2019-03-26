package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.TokenModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenDAOTest {

    private static final String USERNAME = "thijs";
    private static final String TOKEN = "123456789";

    private TokenDAO sut;
    private DatabaseConnection dbConnection;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private TokenModel tokenObj;

    @BeforeEach
    void setup() throws SQLException {
        sut = new TokenDAO();
        tokenObj = new TokenModel();

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
        sut.verifyClientToken(USERNAME);
        // Verify
        verify(connection).prepareStatement(anyString());
    }

    @Test
    void doesMethodVerifyClientTokenSetString() throws SQLException {
        //Setup

        //Test
        sut.verifyClientToken(USERNAME);

        //Verify
        verify(statement).setString(1, USERNAME);
    }

    @Test
    void doesMethodVerifyClientExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.verifyClientToken(USERNAME);

        //Verify
        verify(statement).executeQuery();
    }


    @Test
    void doesMethodVerifyClientTokenHandleSQLExceptionCorrect() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException());

        assertThrows(SQLException.class, () -> {
            sut.verifyClientToken(USERNAME);
        });
    }

    @Test
    void doesMethodInsertTokenIfUsersFirstTimeSetString() throws SQLException {
        //Setup

        //Test
        sut.insertTokenIfUsersFirstTime(USERNAME);

        //Verify
        verify(statement).setString(1, USERNAME);

    }

    @Test
    void doesMethodInsertTokenIfUsersFirstTimeExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.insertTokenIfUsersFirstTime(USERNAME);

        //Verify
        verify(statement).executeQuery();
    }

    @Test
    void doesMethodHasntUserGotATokenYetSetToken() throws SQLException {
        //Setup
        when(result.next()).thenReturn(true);

        //Test
        sut.insertTokenIfUsersFirstTime(USERNAME);

        //Verify
        assertNotNull(sut.getToken());
    }
}
