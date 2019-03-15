package nl.thijs.dea.controllers.dto;

import nl.thijs.dea.models.TrackModel;

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