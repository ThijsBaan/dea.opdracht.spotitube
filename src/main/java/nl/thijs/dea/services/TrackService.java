package nl.thijs.dea.services;

import nl.thijs.dea.datasources.dao.TrackDAO;
import nl.thijs.dea.services.dto.TrackResponseDto;
import nl.thijs.dea.services.models.TrackModel;

import javax.inject.Inject;

public class TrackService {
    private TrackDAO trackDAO;

    @Inject
    public void setDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    public TrackResponseDto loadTracksPerPlaylist(int forPlayList) {
        var response = new TrackResponseDto();

        response.setTracks(trackDAO.getAllTracksWhoAreInPlaylist(forPlayList));
        return response;
    }

    public void deleteTrackFromPlaylist(String token, int forPlayList, int trackID) {
        trackDAO.deleteTrackFromPlaylist(token, forPlayList, trackID);
    }

    public void addTrackToPlaylist(int forPlayList,
                                       TrackModel request){
        trackDAO.addTrackToPlaylist(forPlayList, request.getId(), request.isOfflineAvailable());
    }

    public TrackResponseDto loadTracksForAdd(int forPlayList){
        var response = new TrackResponseDto();
        response.setTracks(trackDAO.getAllTracksWhoArentInPlaylist(forPlayList));
        return response;
    }
}
