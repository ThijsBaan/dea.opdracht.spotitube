package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.services.models.TokenModel;
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
    private SQLException sqlException;

    @BeforeEach
    void setup() throws SQLException {
        tokenObj = new TokenModel();
        sqlException = mock(SQLException.class);
        dbConnection = mock(DatabaseConnection.class);

        connection = Mockito.mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);

        when(statement.executeQuery()).thenReturn(result);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(dbConnection.getConnection()).thenReturn(connection);

        sut = new TokenDAO(dbConnection);
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

    @Test
    void doesMethodHasntUserGotATokenYetHandleSQLExceptionCorrect() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlException);

        sut.insertTokenIfUsersFirstTime(USERNAME);

        verify(sqlException).printStackTrace();
    }


    @Test
    void doesMethodInsertTokenIfUsersFirstTimeHandleSQLExceptionCorrect() throws SQLException {
        //Setup
        when(result.next()).thenReturn(false);
        when(statement.executeUpdate()).thenThrow(sqlException);

        sut.insertTokenIfUsersFirstTime(USERNAME);

        verify(sqlException).printStackTrace();
    }

    @Test
    void doesMethodVerifyClientTokenHandleSQLExceptionCorrect() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlException);

        //Test
        sut.verifyClientToken(USERNAME);

        //Verify
        verify(sqlException).printStackTrace();
    }
}
