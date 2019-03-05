package nl.thijs.dea.dto;

import nl.thijs.dea.dummy.DummyPlaylists;

import java.util.List;

/**
 * PlaylistRequestDto --- class for making an request for Playlist
 * @author Thijs
 */
public class PlaylistRequestDto {
    private List<DummyPlaylists> playlists;
    private int length;

    public List<DummyPlaylists> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<DummyPlaylists> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
