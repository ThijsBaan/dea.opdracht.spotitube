package nl.thijs.dea.controllers;

import nl.thijs.dea.services.TrackService;
import nl.thijs.dea.services.dto.TrackResponseDto;
import nl.thijs.dea.services.models.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TrackControllerTest {

    private TrackService trackServiceMock;
    private TrackController sut;

    private List<TrackModel> trackList = new ArrayList<>();

    private static final String TOKEN = "123456789";
    private static final int ID = 1;
    private static final String TITLE = "MuziekTitel";
    private static final String PERFORMER = "artiest";
    private static final int DURATION = 5;
    private static final String ALBUM = "deel 2";
    private static final int PLAYCOUNT = 0;
    private static final String PUBLICATIONDATE = "22-03-2019";
    private static final String DESCRIPTION = "Looong";
    private static final Boolean OFFLINEAVAILABLE = false;

    private static final int PLAYLIST = 1;
    private TrackModel track;
    private TrackResponseDto response;

    @BeforeEach
    void setup() {
        trackServiceMock = mock(TrackService.class);

        sut = new TrackController();
        sut.setService(trackServiceMock);

        track = new TrackModel(ID, TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE, DESCRIPTION,
                OFFLINEAVAILABLE);
        trackList.add(track);
        response = new TrackResponseDto();
        response.setTracks(trackList);

    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(trackServiceMock.loadTracksPerPlaylist(ID)).thenReturn(response);

        // Test
        Response result = sut.loadTracksPerPlaylist(ID);

        // Verify
        verify(trackServiceMock).loadTracksPerPlaylist(ID);
    }


    @Test
    void checkIfMethodCanDeleteTrackFromPlaylist() {
        // Setup
        doNothing().when(trackServiceMock).deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);

        // Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);

        // Verify
        verify(trackServiceMock).deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);
    }

    @Test
    void checkIfMethodCanAddTrackFromPlaylist() {
        // Setup
        doNothing().when(trackServiceMock).addTrackToPlaylist(PLAYLIST, track);

        // Test
        sut.addTrackToPlaylist(PLAYLIST, track);

        // Verify
        verify(trackServiceMock).addTrackToPlaylist(PLAYLIST, track);
    }

    @Test
    void checkIfMethodCanloadTracksForAdd() {
        // Setup
        when(trackServiceMock.loadTracksForAdd(PLAYLIST)).thenReturn(response);

        // Test
        Response result = sut.loadTracksForAdd(PLAYLIST);

        // Verify
        verify(trackServiceMock).loadTracksForAdd(PLAYLIST);
    }


}
