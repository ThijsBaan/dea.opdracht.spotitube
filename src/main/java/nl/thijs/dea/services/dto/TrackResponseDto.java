package nl.thijs.dea.services.dto;

import nl.thijs.dea.services.models.TrackModel;

import java.util.List;

public class TrackResponseDto {
    private List<TrackModel> tracks;

    public List<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackModel> tracks) {
        this.tracks = tracks;
    }
}