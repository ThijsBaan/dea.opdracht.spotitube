package nl.thijs.dea.controllers.dto;

import nl.thijs.dea.models.PlaylistModel;

import java.util.List;

/**
 * PlaylistResponseDto --- class for making an request for PlaylistController
 * @author Thijs
 */
public class PlaylistResponseDto {
    private List<PlaylistModel> playlists;
    private int length;

    public List<PlaylistModel> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistModel> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
