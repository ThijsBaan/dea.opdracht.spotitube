package nl.thijs.dea.dto;

import nl.thijs.dea.dummy.DummyTracks;

import java.util.List;

public class TrackResponseDto {
    private List<DummyTracks> tracks;

    public List<DummyTracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<DummyTracks> tracks) {
        this.tracks = tracks;
    }
}