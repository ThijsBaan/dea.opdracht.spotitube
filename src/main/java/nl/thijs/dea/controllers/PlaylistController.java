package nl.thijs.dea.controllers;

import nl.thijs.dea.dto.PlaylistRepsonseDto;
import nl.thijs.dea.dto.TrackResponseDto;
import nl.thijs.dea.dummy.DummyPlaylists;
import nl.thijs.dea.dummy.DummyTracks;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * PlaylistController --- class for letting user view the playlists
 *
 * @author Thijs
 */
@Path("/")
@Produces("application/json")
public class PlaylistController {
    private List<DummyPlaylists> musicPlaylist = new ArrayList<>();
    private List<DummyTracks> trackList = new ArrayList<>();
    private int playlistLength = 0;

    /**
     * Constructor:
     * add two DummyPlaylists for PlaylistController and add them to the ArrayList musicPlaylist.
     */
    public PlaylistController() {
        trackList.add(new DummyTracks(3, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", false));
        trackList.add(new DummyTracks(4, "So Long, Marianne", "Leonard Cohen", 546, "Songs of Leonard Cohen", false));
        trackList.add(new DummyTracks(5, "One", "Metallica", 423, "", 37, "1-11-2001", "Long version", false));

        int[] tracksVoorPlaylist1 = {4, 3};
        int[] tracksVoorPlaylist2 = {5};
        int[] tracksVoorPlaylist3 = {5, 4, 3};

        musicPlaylist.add(new DummyPlaylists(1, "Death metal", true, tracksVoorPlaylist1));
        musicPlaylist.add(new DummyPlaylists(2, "Pop", false, tracksVoorPlaylist2));
        musicPlaylist.add(new DummyPlaylists(3, "Disney", true, tracksVoorPlaylist3));
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response geefPlaylists(@QueryParam("token") String token) {
        if ("ABCDEFG".equals(token)) { //"ABCDEFG" vervangen voor opgeslagen code uit database
            PlaylistRepsonseDto response = new PlaylistRepsonseDto();

            for (DummyPlaylists playlist : musicPlaylist) {
                for (DummyTracks track : trackList) {
                    for (int i = 0; i < playlist.getTracks().length; i++) {
                        if (track.getId() == playlist.getTracks()[i]) {
                            playlistLength += track.getDuration();
                        }
                    }
                }
            }

            response.setPlaylists(musicPlaylist);
            response.setLength(playlistLength);

            return Response.ok().entity(response).build();
        } else if ("".equals(token)) {
            return Response.status(400).build();
        } else {
            return Response.status(403).build();
        }
    }

    @GET
    @Path("playlists/{forplaylist}/tracks")
    public Response geefTracks(@PathParam("forplaylist") int forPlayList, @QueryParam("token") String token) {
        List<DummyTracks> tempTrackList = new ArrayList<>();

        for (DummyPlaylists playlist : musicPlaylist) {
            if (playlist.getId() == forPlayList) {
                for (DummyTracks track : trackList) {
                    for (int trackid : playlist.getTracks()) {
                        if (trackid == track.getId()) {
                            tempTrackList.add(track);
                        }
                    }
                }

                var response = new TrackResponseDto();
                response.setTracks(tempTrackList);
                return Response.ok().entity(response).build();
            }
        }
        return null;
    }
}
