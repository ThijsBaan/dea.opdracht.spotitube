package nl.thijs.dea.controllers;

import nl.thijs.dea.controllers.dto.TrackResponseDto;
import nl.thijs.dea.datasources.dao.TrackDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("playlists")
public class TrackController {
    private TrackDAO trackDAO;

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @GET
    @Path("/{forplaylist}/tracks")
    public Response loadTracks(@PathParam("forplaylist") int forPlayList) {
        var response = new TrackResponseDto();

        response.setTracks(trackDAO.getAllTracksPerPlaylist(forPlayList));
        return Response.ok().entity(response).build();
    }
}
