package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class TrackDAOTest {

    private static final String USERNAME = "thijs";
    private static final String TOKEN = "123456789";

    private TrackDAO sut;
    private DatabaseConnection dbConnection;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private static final int PLAYLISTID = 1;


    private static final int ID = 1;
    private static final String TITEL = "disney";
    private static final String PERFORMER = "Ties";
    private static final int DURATION = 50;
    private static final String ALBUM = "DISNEEYYYY";
    private static final int PLAYCOUNT = 0;
    private static final String PUBLICATIONDATE = "26-03-2019" ;
    private static final String DESCRIPTION = "best album ever!";
    private static final boolean OFFLINEAVAILABLE = false;
    private TrackModel trackObj;

    @BeforeEach
    void setup() throws SQLException {
        sut = new TrackDAO();
        trackObj = new TrackModel(ID, TITEL, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE,DESCRIPTION, OFFLINEAVAILABLE);

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
        sut.getAllTracksWhoArentInPlaylist(PLAYLISTID);
        // Verify
        verify(connection).prepareStatement(anyString());
    }

}
