package nl.thijs.dea.controllers;

import nl.thijs.dea.datasources.dao.TrackDAO;
import nl.thijs.dea.models.TrackModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrackControllerTest {

    private TrackDAO trackDAOMock;
    private TrackController sut;

    private List<TrackModel> trackList = new ArrayList<>();

    private static final int ID = 1;
    private static final String TITLE = "MuziekTitel";
    private static final String PERFORMER = "artiest";
    private static final int DURATION = 5;
    private static final String ALBUM = "deel 2";
    private static final int PLAYCOUNT = 0;
    private static final String PUBLICATIONDATE = "22-03-2019";
    private static final String DESCRIPTION = "Looong";
    private static final Boolean OFFLINEAVAILABLE = false;

    @BeforeEach
    void setup() {
        trackDAOMock = mock(TrackDAO.class);

        sut = new TrackController();
        sut.setDAO(trackDAOMock);

        trackList.add(new TrackModel(ID, TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATIONDATE, DESCRIPTION,
                OFFLINEAVAILABLE));
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(trackDAOMock.getAllTracksWhoAreInPlaylist(ID)).thenReturn(trackList);

        // Test
        Response result = sut.loadTracksPerPlaylist(ID);

        // Verify
        verify(trackDAOMock).getAllTracksWhoAreInPlaylist(ID);
    }
}
