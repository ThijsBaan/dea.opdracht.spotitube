package nl.thijs.dea.controllers;

import nl.thijs.dea.datasources.dao.PlayListDAO;
import nl.thijs.dea.models.PlaylistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {
    private PlayListDAO playlistDAOMockup;
    private PlaylistController sut;
    private PlaylistModel playlist;

    private static final String TOKEN = "23456789";
    private static final int ID = 1;
    private static final String TITEL = "Disney";
    private static final Boolean OWNER = false;
    private static final int[] TRACKS = new int[0];

    @BeforeEach
    void setup(){
        playlistDAOMockup = mock(PlayListDAO.class);
        sut = new PlaylistController();
        sut.setPlaylistDAO(playlistDAOMockup);

        playlist = new PlaylistModel(ID, TITEL, OWNER, TRACKS);
    }


    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(playlistDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        verify(playlistDAOMockup).verifyClientToken(TOKEN);
    }

    @Test
    void checkIfResponse400IsGivenWhenTokenEmpty() {
        // Setup
        when(playlistDAOMockup.verifyClientToken("")).thenReturn(false);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(400, result.getStatus());

        //verify(playlistDAOMockup).verifyClientToken(TOKEN);
    }

    @Test
    void checkIfResponse403IsGivenWhenTokenNotExists() {
        // Setup
        when(playlistDAOMockup.verifyClientToken(null)).thenReturn(true);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(403, result.getStatus());

        //verify(playlistDAOMockup).verifyClientToken(TOKEN);
    }


}
