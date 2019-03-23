package nl.thijs.dea.controllers;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import nl.thijs.dea.controllers.dto.PlaylistRequestDto;
import nl.thijs.dea.controllers.dto.PlaylistResponseDto;
import nl.thijs.dea.datasources.dao.PlayListDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.models.PlaylistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistControllerTest {
    private PlayListDAO playlistDAOMockup;
    private TokenDAO tokenDAOMockup;
    private PlaylistController sut;
    private PlaylistModel playlist;
    private PlaylistRequestDto request;

    private static final String TOKEN = "23456789";
    private static final int ID = 1;
    private static final String TITEL = "Disney";
    private static final Boolean OWNER = false;
    private static final int[] TRACKS = new int[0];

    private List<PlaylistModel> plm = new ArrayList<>();
    private Response expectedResponse;
    @BeforeEach
    void setup(){
        playlistDAOMockup = mock(PlayListDAO.class);
        tokenDAOMockup = mock(TokenDAO.class);
        sut = new PlaylistController();
        sut.setDAO(playlistDAOMockup, tokenDAOMockup);

        playlist = new PlaylistModel(ID, TITEL, OWNER, TRACKS);
        plm.add(playlist);
        request = new PlaylistRequestDto();
        request.setName(TITEL);

        PlaylistResponseDto response = new PlaylistResponseDto();
        response.setPlaylists(playlistDAOMockup.loadPlaylists(TOKEN));
        response.setLength(playlistDAOMockup.getTotalPlaylistLength());

        expectedResponse = Response.ok().entity(response).build();
    }


    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        verify(tokenDAOMockup).verifyClientToken(TOKEN);
    }

    @Test
    void checkIfResponse400IsGivenWhenTokenEmpty() {
        // Setup
        when(tokenDAOMockup.verifyClientToken("")).thenReturn(false);

        // Test
        Response result = sut.loadPlaylists("");

        // Verify

        assertEquals(400, result.getStatus());
    }

    @Test
    void checkIfResponse403IsGivenWhenTokenNotExists() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(null)).thenReturn(true);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(403, result.getStatus());
    }

    @Test
    void checkIfMethodCanAddPlaylist() {
        // Setup
        doNothing().when(playlistDAOMockup).addPlaylist(TOKEN, TITEL);

        // Test
         sut.addPlaylist(TOKEN, request);

        // Verify
        verify(playlistDAOMockup).addPlaylist(TOKEN, TITEL);
    }

    @Test
    void checkIfMethodCanDeletePlaylist() {
        // Setup
        doNothing().when(playlistDAOMockup).deletePlaylist(TOKEN, ID);

        // Test
        sut.deletePlaylist(TOKEN, ID);

        // Verify
        verify(playlistDAOMockup).deletePlaylist(TOKEN, ID);
    }

    @Test
    void checkIfMethodCanEditPlaylist() {
        // Setup
        doNothing().when(playlistDAOMockup).editPlaylist(TOKEN, ID, TITEL);

        // Test
        sut.editPlaylist(TOKEN, ID, request);

        // Verify
        verify(playlistDAOMockup).editPlaylist(TOKEN, ID, TITEL);
    }

    @Test
    void checkIfMethodReturnsCorrectLoadedPlaylist() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        Response r = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(expectedResponse.toString(), r.toString());
    }

    @Test
    void checkIfMethodReturnsCorrectLoadedPlaylistAfterAdd() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        Response r = sut.addPlaylist(TITEL, request);

        // Verify
        assertEquals(expectedResponse.toString(), r.toString());
    }
}
