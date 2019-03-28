package nl.thijs.dea.controllers;

import nl.thijs.dea.services.PlaylistService;
import nl.thijs.dea.services.dto.PlaylistRequestDto;
import nl.thijs.dea.datasources.dao.TokenDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * PlaylistController --- class for letting user view the playlists
 *
 * @author Thijs
 */
@Path("/")
@Produces("application/json")
public class PlaylistController {
    private PlaylistService playlistService;
    private TokenDAO tokenDAO;

    @Inject
    public void setService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Path("playlists")
    @Produces("application/json")
    public Response loadPlaylists(@QueryParam("token") String token) {
        var response = playlistService.loadPlaylists(token);

        if (response == null) {
            return Response.status(403).build();
        }
        return Response.ok().entity(response).build();

    }

    @POST
    @Path("playlists")
    @Consumes("application/json")
    public Response addPlaylist(@QueryParam("token") String token, PlaylistRequestDto request) {
        playlistService.addPlaylist(token, request);
        return loadPlaylists(token);
    }

    @DELETE
    @Path("playlists/{id}")
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        playlistService.deletePlaylist(token, id);
        return loadPlaylists(token);
    }

    @PUT
    @Path("playlists/{id}")
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id,
                                 PlaylistRequestDto request) {
        playlistService.editPlaylist(token, id, request);
        return loadPlaylists(token);
    }
}
