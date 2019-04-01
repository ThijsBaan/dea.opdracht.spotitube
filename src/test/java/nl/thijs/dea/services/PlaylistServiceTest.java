package nl.thijs.dea.services;

import nl.thijs.dea.services.dto.PlaylistRequestDto;
import nl.thijs.dea.services.dto.PlaylistResponseDto;
import nl.thijs.dea.datasources.dao.PlayListDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.services.models.PlaylistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {


    private PlayListDAO playlistDAOMockup;
    private TokenDAO tokenDAOMockup;
    private PlaylistService sut;
    private PlaylistModel playlist;
    private PlaylistRequestDto request;

    private static final String TOKEN = "23456789";
    private static final int ID = 1;
    private static final String TITEL = "Disney";
    private static final Boolean OWNER = false;
    private static final int[] TRACKS = new int[0];

    private List<PlaylistModel> plm = new ArrayList<>();
    private PlaylistResponseDto response;
    @BeforeEach
    void setup() {
        playlistDAOMockup = mock(PlayListDAO.class);
        tokenDAOMockup = mock(TokenDAO.class);
        sut = new PlaylistService();
        sut.setDAO(playlistDAOMockup, tokenDAOMockup);

        playlist = new PlaylistModel(ID, TITEL, OWNER, TRACKS);
        plm.add(playlist);
        request = new PlaylistRequestDto();
        request.setName(TITEL);

        response = new PlaylistResponseDto();
        response.setPlaylists(playlistDAOMockup.loadPlaylists(TOKEN));
        response.setLength(playlistDAOMockup.getTotalPlaylistLength());
    }


    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        PlaylistResponseDto result = sut.loadPlaylists(TOKEN);

        // Verify
        verify(tokenDAOMockup).verifyClientToken(TOKEN);
    }

    @Test
    void checkIfResponseNullIsGivenWhenTokenNotExists() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(anyString())).thenReturn(false);

        // Test
        PlaylistResponseDto result = sut.loadPlaylists(TOKEN);

        // Verify
        assertNull(result);
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
        PlaylistResponseDto r = sut.loadPlaylists(TOKEN);

        // Verify
        assertEquals(response.getLength(), r.getLength());
    }

    @Test
    void checkIfMethodReturnsCorrectLoadedPlaylistAfterAdd() {
        // Setup
        when(tokenDAOMockup.verifyClientToken(TOKEN)).thenReturn(true);

        // Test
        sut.addPlaylist(TITEL, request);

        // Verify
        //assertEquals(expectedResponse.toString(), r.toString());
    }
}
