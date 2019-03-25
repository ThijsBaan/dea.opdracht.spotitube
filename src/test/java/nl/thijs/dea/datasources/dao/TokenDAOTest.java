package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TokenDAOTest {
    private DatabaseConnection databaseMock;
    private Connection connectionMock;
    private TokenDAO sut;

    private static final String USERNAME = "thijs";
    private static final String TOKEN = "123456789";


    @BeforeEach
    void setup(){
        databaseMock = mock(DatabaseConnection.class);
        connectionMock = databaseMock.getConnection();

        sut = new TokenDAO();
        sut.setConnection(databaseMock);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(databaseMock.getConnection()).thenReturn(connectionMock);

        // Test
        sut.setConnection(databaseMock);

        // Verify
        verify(databaseMock).getConnection();
    }

    @Test
    void checkIfTokenReturnsCorrecBoolean(){
        // setup

        // test
        boolean verifier = sut.verifyClientToken(TOKEN);

        // verify
        assertTrue(verifier);
    }
}
