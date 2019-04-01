package nl.thijs.dea.services;

import nl.thijs.dea.datasources.dao.TrackDAO;
import nl.thijs.dea.services.dto.TrackResponseDto;
import nl.thijs.dea.services.models.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TrackServiceTest {

    private TrackDAO trackDAOMock;
    private TrackService sut;

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

    @BeforeEach
    void setup() {
        trackDAOMock = mock(TrackDAO.class);

        sut = new TrackService();
        sut.setDAO(trackDAOMock);

        track = new TrackModel(ID, TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE, DESCRIPTION,
                OFFLINEAVAILABLE);
        trackList.add(track);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(trackDAOMock.getAllTracksWhoAreInPlaylist(ID)).thenReturn(trackList);

        // Test
        TrackResponseDto result = sut.loadTracksPerPlaylist(ID);

        // Verify
        verify(trackDAOMock).getAllTracksWhoAreInPlaylist(ID);
    }

    @Test
    void checkIfMethodCanDeleteTrackFromPlaylist() {
        // Setup
        doNothing().when(trackDAOMock).deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);

        // Test
        sut.deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);

        // Verify
        verify(trackDAOMock).deleteTrackFromPlaylist(TOKEN, PLAYLIST, ID);
    }

    @Test
    void checkIfMethodCanAddTrackFromPlaylist() {
        // Setup
        doNothing().when(trackDAOMock).addTrackToPlaylist(PLAYLIST, ID, OFFLINEAVAILABLE);

        // Test
        sut.addTrackToPlaylist(PLAYLIST, track);

        // Verify
        verify(trackDAOMock).addTrackToPlaylist(PLAYLIST, ID, OFFLINEAVAILABLE);
    }

    @Test
    void checkIfMethodCanloadTracksForAdd() {
        // Setup
        when(trackDAOMock.getAllTracksWhoArentInPlaylist(PLAYLIST)).thenReturn(trackList);

        // Test
        TrackResponseDto result = sut.loadTracksForAdd(PLAYLIST);

        // Verify
        verify(trackDAOMock).getAllTracksWhoArentInPlaylist(PLAYLIST);
    }
}