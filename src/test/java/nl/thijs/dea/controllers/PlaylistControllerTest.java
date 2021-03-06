package nl.thijs.dea.controllers;

import nl.thijs.dea.services.PlaylistService;
import nl.thijs.dea.services.dto.PlaylistRequestDto;
import nl.thijs.dea.services.dto.PlaylistResponseDto;
import nl.thijs.dea.datasources.dao.PlayListDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.services.models.PlaylistModel;
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
    private PlaylistService playlistServiceMock;
    private PlaylistResponseDto response;

    @BeforeEach
    void setup() {
        sut = new PlaylistController();
        playlistServiceMock = mock(PlaylistService.class);
        sut.setService(playlistServiceMock);

        playlist = new PlaylistModel(ID, TITEL, OWNER, TRACKS);
        plm.add(playlist);
        request = new PlaylistRequestDto();
        request.setName(TITEL);

        response = new PlaylistResponseDto();
        response.setPlaylists(plm);
        response.setLength(5);

        expectedResponse = Response.ok().entity(response).build();

    }


    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(playlistServiceMock.loadPlaylists(TOKEN)).thenReturn(response);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        verify(playlistServiceMock).loadPlaylists(TOKEN);
    }

    @Test
    void checkIfResponse403IsGivenWhenTokenNotExists() {
        // Setup
        when(playlistServiceMock.loadPlaylists(TOKEN)).thenReturn(null);

        // Test
        Response result = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(403, result.getStatus());
    }

    @Test
    void checkIfMethodCanAddPlaylist() {
        // Setup
        doNothing().when(playlistServiceMock).addPlaylist(TOKEN, request);

        // Test
        sut.addPlaylist(TOKEN, request);

        // Verify
        verify(playlistServiceMock).addPlaylist(TOKEN, request);
    }

    @Test
    void checkIfMethodCanDeletePlaylist() {
        // Setup
        doNothing().when(playlistServiceMock).deletePlaylist(TOKEN, ID);

        // Test
        sut.deletePlaylist(TOKEN, ID);

        // Verify
        verify(playlistServiceMock).deletePlaylist(TOKEN, ID);
    }

    @Test
    void checkIfMethodCanEditPlaylist() {
        // Setup
        doNothing().when(playlistServiceMock).editPlaylist(TOKEN, ID, request);

        // Test
        sut.editPlaylist(TOKEN, ID, request);

        // Verify
        verify(playlistServiceMock).editPlaylist(TOKEN, ID, request);
    }

    @Test
    void checkIfMethodReturnsCorrectLoadedPlaylist() {
        // Setup
        when(playlistServiceMock.loadPlaylists(TOKEN)).thenReturn(response);

        // Test
        Response r = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(expectedResponse.toString(), r.toString());
    }
}
