package nl.thijs.dea.controllers;

import nl.thijs.dea.controllers.dto.PlaylistRepsonseDto;
import nl.thijs.dea.controllers.dto.TrackResponseDto;
import nl.thijs.dea.datasources.doa.PlayListDAO;
import nl.thijs.dea.models.PlaylistModel;
import nl.thijs.dea.models.TrackModel;

import javax.inject.Inject;
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
    private List<PlaylistModel> musicPlaylist = new ArrayList<>();
    private List<TrackModel> trackList = new ArrayList<>();
    private int playlistLength = 0;

    private PlayListDAO playlistDAO;

    @Inject
    public void setLoginDAO(PlayListDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    /**
     * Constructor:
     * add two PlaylistModel for PlaylistController and add them to the ArrayList musicPlaylist.
     */
    public PlaylistController() {
        trackList.add(new TrackModel(3, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", false));
        trackList.add(new TrackModel(4, "So Long, Marianne", "Leonard Cohen", 546, "Songs of Leonard Cohen", false));
        trackList.add(new TrackModel(5, "One", "Metallica", 423, "", 37, "1-11-2001", "Long version", false));
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response loadPlaylists(@QueryParam("token") String token) {
        if (playlistDAO.isTheClientTokenValid(token)) {
            PlaylistRepsonseDto response = new PlaylistRepsonseDto();

            for (PlaylistModel playlist : musicPlaylist) {
                for (TrackModel track : trackList) {
                    for (int i = 0; i < playlist.getTracks().length; i++) {
                        if (track.getId() == playlist.getTracks()[i]) {
                            playlistLength += track.getDuration();
                        }
                    }
                }
            }

            response.setPlaylists(playlistDAO.getAllPlaylists(token));
            response.setLength(playlistDAO.getTotalPlaylistLength());

            return Response.ok().entity(response).build();
        } else if ("".equals(token)) {
            return Response.status(400).build();
        } else {
            return Response.status(403).build();
        }
    }
}
