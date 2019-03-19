package nl.thijs.dea.controllers;

import nl.thijs.dea.controllers.dto.PlaylistResponseDto;
import nl.thijs.dea.controllers.dto.PlaylistRequestDto;
import nl.thijs.dea.datasources.dao.PlayListDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * PlaylistController --- class for letting user view the playlists
 *
 * @author Thijs
 */
@Path("playlists")
@Produces("application/json")
public class PlaylistController {
    private PlayListDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(PlayListDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @GET
    @Produces("application/json")
    public Response loadPlaylists(@QueryParam("token") String token) {
        if (playlistDAO.verifyClientToken(token)) {
            PlaylistResponseDto response = new PlaylistResponseDto();

            response.setPlaylists(playlistDAO.loadPlaylists(token));
            response.setLength(playlistDAO.getTotalPlaylistLength());

            return Response.ok().entity(response).build();
        } else if ("".equals(token)) {
            return Response.status(400).build();
        } else {
            return Response.status(403).build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response addPlaylist(@QueryParam("token") String token, PlaylistRequestDto request) {
        playlistDAO.addPlaylist(token, request.getName());
        return loadPlaylists(token);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id){
        playlistDAO.deletePlaylist(token, id);
        return loadPlaylists(token);
    }

    @PUT
    @Path("/{id}")
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id,
                                 PlaylistRequestDto request){
        playlistDAO.editPlaylist(token, id, request.getName());
        return loadPlaylists(token);
    }
}
