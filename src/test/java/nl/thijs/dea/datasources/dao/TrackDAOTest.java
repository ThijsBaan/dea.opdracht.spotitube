package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
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

    private TrackDAO sut;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private SQLException sqlExcpetion;

    private static final String TOKEN = "123456789";
    private static final int PLAYLISTID = 1;
    private static final int ID = 1;
    private static final boolean OFFLINEAVAILABLE = false;

    @BeforeEach
    void setup() throws SQLException {
        DatabaseConnection dbConnection = mock(DatabaseConnection.class);
        sqlExcpetion = mock(SQLException.class);

        connection = Mockito.mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);

        when(statement.executeQuery()).thenReturn(result);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(dbConnection.getConnection()).thenReturn(connection);

        sut = new TrackDAO(dbConnection);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDbConnection() throws SQLException {
        // Setup

        // Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLISTID, ID);
        // Verify
        verify(connection).prepareStatement(anyString());
    }

    @Test
    void doesMethodDeleteTrackFromPlaylistSetString() throws SQLException {
        //Setup

        //Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLISTID, ID);

        //Verify
        verify(statement).setString(1, TOKEN);
    }

    @Test
    void doesMethodDeleteTrackFromPlaylistSetInt() throws SQLException {
        //Setup

        //Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLISTID, ID);

        //Verify
        verify(statement).setInt(2, PLAYLISTID);
    }

    @Test
    void doesMethodDeleteTrackFromPlaylistUpdateQuery() throws SQLException {
        //Setup

        //Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLISTID, ID);

        //Verify
        verify(statement).executeUpdate();
    }

    @Test
    void doesMethodAddTrackToPlaylistSetInt() throws SQLException {
        //Setup

        //Test
        sut.addTrackToPlaylist(PLAYLISTID, ID, OFFLINEAVAILABLE);

        //Verify
        verify(statement).setInt(1, PLAYLISTID);
    }

    @Test
    void doesMethodAddTrackToPlaylistSetBoolean() throws SQLException {
        //Setup

        //Test
        sut.addTrackToPlaylist(PLAYLISTID, ID, OFFLINEAVAILABLE);

        //Verify
        verify(statement).setBoolean(3, OFFLINEAVAILABLE);
    }

    @Test
    void doesMethodAddTrackToPlaylistUpdateQuery() throws SQLException {
        //Setup

        //Test
        sut.addTrackToPlaylist(PLAYLISTID, ID, OFFLINEAVAILABLE);

        //Verify
        verify(statement).executeUpdate();
    }

    @Test
    void doesGetAllTracksWhoArentInPlaylistHandleSQLException() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlExcpetion);

        //Test
        sut.getAllTracksWhoArentInPlaylist(ID);

        //Verify
        verify(sqlExcpetion, times(2)).printStackTrace();
    }

    @Test
    void doesGetAllTracksWhoAreInPlaylistHandleSQLException() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlExcpetion);

        //Test
        sut.getAllTracksWhoAreInPlaylist(ID);

        //Verify
        verify(sqlExcpetion, times(2)).printStackTrace();
    }

    @Test
    void doesGetAllTracksWhoAreInPlaylistHandleResultNext() throws SQLException {
        //Setup
        when(result.next()).thenReturn(true, true, false);

        //Test
        sut.getAllTracksWhoAreInPlaylist(ID);

        //Verify
        verify(result, times(4)).next();
    }

    @Test
    void doesDeleteTrackFromPlaylistHandleSQLException() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlExcpetion);

        //Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLISTID, ID);

        //Verify
        verify(sqlExcpetion).printStackTrace();
    }

    @Test
    void doesAddTrackFromPlaylistHandleSQLException() throws SQLException {
        //Setup
        when(connection.prepareStatement(anyString())).thenThrow(sqlExcpetion);

        //Test
        sut.addTrackToPlaylist(PLAYLISTID, ID, OFFLINEAVAILABLE);

        //Verify
        verify(sqlExcpetion).printStackTrace();
    }
}
