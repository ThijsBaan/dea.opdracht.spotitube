package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.models.PlaylistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class PlaylistDAOTest {

    private static final String USERNAME = "thijs";
    private static final String TOKEN = "123456789";

    private static final int ID = 1;
    private static final String NAAM = "Disney";
    private static final Boolean OWNER = false;
    private static final int[] TRACKS = {1,2};

    private PlayListDAO sut;
    private DatabaseConnection dbConnection;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private PlaylistModel playlistObj;

    @BeforeEach
    void setup() throws SQLException {
        sut = new PlayListDAO();
        playlistObj = new PlaylistModel(ID, NAAM, OWNER, TRACKS);

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
        sut.loadPlaylists(TOKEN);
        // Verify
        verify(connection).prepareStatement(anyString());
    }

    @Test
    void doesMethodLoadPlaylistsTokenSetString() throws SQLException {
        //Setup

        //Test
        sut.loadPlaylists(TOKEN);

        //Verify
        verify(statement).setString(1, TOKEN);
    }

    @Test
    void doesMethodLoadPlaylistsExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.loadPlaylists(TOKEN);

        //Verify
        verify(statement).executeQuery();
    }

    @Test
    void doesMethodLoadPlaylistsIncreaseLength() throws SQLException {
        //Setup
        when(result.next()).thenReturn(true).thenReturn(false);
        when(result.getInt("duration")).thenReturn(5);

        //Test
        int lengte = sut.getTotalPlaylistLength();
        sut.loadPlaylists(TOKEN);

        //Verify
        assertTrue(lengte < sut.getTotalPlaylistLength());
    }

    @Test
    void doesMethodAddPlaylistsTokenSetString() throws SQLException {
        //Setup

        //Test
        sut.addPlaylist(TOKEN, NAAM);

        //Verify
        verify(statement).setString(1, NAAM);
    }

    @Test
    void doesMethodAddPlaylistsExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.addPlaylist(TOKEN, NAAM);

        //Verify
        verify(statement).executeUpdate();
    }

    @Test
    void doesMethodDeletePlaylistsTokenSetString() throws SQLException {
        //Setup

        //Test
        sut.deletePlaylist(TOKEN, ID);

        //Verify
        verify(statement).setString(2, TOKEN);
    }

    @Test
    void doesMethodDeletePlaylistsTokenSetInt() throws SQLException {
        //Setup

        //Test
        sut.deletePlaylist(TOKEN, ID);

        //Verify
        verify(statement).setInt(1, ID);
    }

    @Test
    void doesMethodDeletePlaylistsExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.deletePlaylist(TOKEN, ID);

        //Verify
        verify(statement).executeUpdate();
    }

    @Test
    void doesMethodEditPlaylistsTokenSetString() throws SQLException {
        //Setup

        //Test
        sut.editPlaylist(TOKEN, ID, NAAM);

        //Verify
        verify(statement).setString(1, NAAM);
    }

    @Test
    void doesMethodEditPlaylistsTokenSetInt() throws SQLException {
        //Setup

        //Test
        sut.editPlaylist(TOKEN, ID, NAAM);

        //Verify
        verify(statement).setInt(2, ID);
    }

    @Test
    void doesMethodEditPlaylistsExecuteQuery() throws SQLException {
        //Setup

        //Test
        sut.editPlaylist(TOKEN, ID, NAAM);

        //Verify
        verify(statement).executeUpdate();
    }

    @Test
    void doesMethodLoadPlaylistTokenHandleSQLExceptionCorrect() throws SQLException {
        fail();
    }

    @Test
    void doesMethodAddPlaylistTokenHandleSQLExceptionCorrect() throws SQLException {
        fail();
    }

    @Test
    void doesMethodDeletePlaylistTokenHandleSQLExceptionCorrect() throws SQLException {
        fail();
    }

    @Test
    void doesMethodEditPlaylistTokenHandleSQLExceptionCorrect() throws SQLException {
        fail();
    }

}
