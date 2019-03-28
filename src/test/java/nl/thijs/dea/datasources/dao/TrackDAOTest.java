package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.services.models.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
    private SQLException sqlExcpetion;

    @BeforeEach
    void setup() throws SQLException {
        trackObj = new TrackModel(ID, TITEL, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE,DESCRIPTION, OFFLINEAVAILABLE);

        dbConnection = mock(DatabaseConnection.class);
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
    void doesGetAllTracksWhoArentInPlaylistReturnTrackModelList() throws SQLException {
        //Setup
//        List<TrackModel> realList = new ArrayList<>();
//        realList.add(trackObj);

        when(result.next()).thenReturn(true).thenReturn(false);
        //when(sut.getAllTracksWhoArentInPlaylist(PLAYLISTID)).thenReturn(realList);
        //Test
        List<TrackModel> tempList = new ArrayList<>();
        tempList = sut.getAllTracksWhoArentInPlaylist(PLAYLISTID);

        //Verify
        //assertEquals(realList, tempList);
        fail();
    }

    @Test
    void doesGetAllTracksWhoAreInPlaylistReturnTrackModelList() throws SQLException {
        //Setup
        List<TrackModel> realList = new ArrayList<>();
        realList.add(trackObj);

        when(result.next()).thenReturn(true).thenReturn(false);
        when(sut.getAllTracksWhoArentInPlaylist(PLAYLISTID)).thenReturn(realList);
        //Test
        List<TrackModel> tempList = new ArrayList<>();
        tempList = sut.getAllTracksWhoAreInPlaylist(PLAYLISTID);

        //Verify
        //assertEquals(realList, tempList);
        fail();
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
